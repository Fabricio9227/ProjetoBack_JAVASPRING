package com.projetobackend.infra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    //FAZENDO A INJEÇÃO NAS CLASSES DEPENDENTES DO RESTTEMPLATE
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
