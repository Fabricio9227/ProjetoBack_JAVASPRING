package com.projetobackend.services;

import com.projetobackend.domain.user.User;
import com.projetobackend.dtos_DATA_TRANSFER_OBJECTS.NotificationDTO;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    @Autowired
    private RestTemplate restTemplate;

    public void sendNotification(User user, String message) throws Exception {
        String email = user.getEmail();
        NotificationDTO notificationRequest = new NotificationDTO(message, email); //Instanciamos um novo objeto da classe "NotificationDTO" que recebe os parâmetros exigidos

//        ResponseEntity<String> notificationResponse = restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, String.class);
//        if(!(notificationResponse.getStatusCode() == HttpStatus.OK)) {
//            System.out.println("Erro ao enviar notificação");
//            throw new Exception("Serviço de notificação fora do ar");
//        }
        System.out.println("Notificação enviada para o usuário");
    }
}
