package com.projetobackend.domain.user;

import com.projetobackend.dtos_DATA_TRANSFER_OBJECTS.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity(name="users")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of="id")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String primeiroNome;
    private String ultimoNome;

    @Column(unique = true)
    private String documento;
    @Column(unique = true)
    private String email;
    private String senha;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private UserType userType;


    //CRIADO UM CONSTRUTOR QUE SERÁ CHAMADO NA CRIAÇÃO DE UM NOVO USUÁRIO
    public User(UserDTO data) {
        this.primeiroNome = data.primeiroNome();
        this.ultimoNome = data.ultimoNome();
        this.balance = data.balance();
        this.userType = data.userType();
        this.password = data.password();
        this.documento = data.documento();
        this.email = data.email();
    }
}
