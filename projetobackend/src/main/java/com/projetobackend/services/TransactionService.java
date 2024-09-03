package com.projetobackend.services;

import com.projetobackend.domain.transaction.Transaction;
import com.projetobackend.domain.transaction.TransactionRepository;
import com.projetobackend.domain.user.User;
import com.projetobackend.dtos_DATA_TRANSFER_OBJECTS.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate; //Através dessa dependencia, podemos fazer chamadas https do tipo GET, POST e REQUEST

    @Autowired
    private NotificationService notificationService;

    //VALIDAÇÃO DE TRANSAÇÃO E TIPO DE USUÁRIO
    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderID());
        User receiver = this.userService.findUserById(transaction.receiverID());

        userService.validadeTransaction(sender, transaction.value()); //Faz a validação primeiro do usuário se ele pode receber aquela transação, depois faz a validação se existe saldo na carteira digital de quem está fazendo a transação

        //VALIDA SE A TRANSAÇÃO ESTÁ AUTORIZADA
        boolean estaAutorizado = this.autorizaçãoTransaction(sender, transaction.value()); //Cria a variável "estaAutoizado" que recebe como parâmetro o método que faz a validação de usuário (que não pode ser MECHANT) e valida o saldo da carteira digital

        if (!estaAutorizado) { //Se não estiver autorizado, lança uma Exception
            throw new Exception("Este usuario no esta autorizado");
        }
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        //ATUALIZANDO O NOVO SALDO DO SENDER
        sender.setBalance(sender.getBalance().subtract(transaction.value())); //Pega o meu saldo atual do pagante e subtrai o valor do saldo em conta pelo valor transferido

        //ATUALIZANDO O NOVO SALDO DO RECEIVER
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        //SALVANDO A NOVA TRANSAÇÃO
        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);


        //NOTIFICANDO O SENDER E O RECEIVER
        this.notificationService.sendNotification(sender, "Trasação realizada com sucesso");

        this.notificationService.sendNotification(receiver, "Trasação recebida com sucesso");

        return newTransaction;
    }


    //VALIDAÇÃO DA CONEXÃO HTTPS
    public boolean autorizaçãoTransaction(User sender, BigDecimal value) throws Exception {
        ResponseEntity<Map> autorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        if (autorizationResponse.getStatusCode() == HttpStatus.OK) { //Se o autorizationResponse tiver um httpStatus de "OK"
            String message = (String) autorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else return false;
    }
}
