package com.projetobackend.services;

import com.projetobackend.domain.user.User;
import com.projetobackend.domain.user.UserType;
import com.projetobackend.dtos_DATA_TRANSFER_OBJECTS.UserDTO;
import com.projetobackend.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepositories repository;

    public void validadeTransaction(User sender, BigDecimal amount) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT){
                throw new Exception("Usuário do tipo Lojista não está autorizado a realizar transação");
        }
        if(sender.getBalance().compareTo(amount) < 0){
            throw new Exception("Saldo insuficiente");
        }


    }
    //CRIADO O MÉTODO DE SALVAR UM USUÁRIO
    public void saveUser(User user) {
        this.repository.save(user);
    }

    //CRIADO O MÉTODO DE BUSCAR UM USUÁRIO PELO ID
    public User findUserById(Integer id) throws Exception {
        return this.repository.findById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
    }

    //MÉTODO QUE CRIA UM NOVO USUÁRIO
    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    //RETORNA UMA LISTA DE USUÁRIOS
    public List<User> getAllUsers() {
        return this.repository.findAll();
    }


}
