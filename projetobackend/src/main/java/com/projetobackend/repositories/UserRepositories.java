package com.projetobackend.repositories;

import com.projetobackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepositories extends JpaRepository<User, Integer> { //UserRepositories vai ser uma classe filha de "JpaRepositories", que vai receber dois tipos de dados, sendo eles um da tabela de "Users" e outro como "Integer", coletando o ID do User

    Optional<User> findByDocumento(String documento); //Criando um método que busca o usuário pelo documento
    Optional<User> findById(Integer id); //Criando um método que busca o usuário pelo ID
}
