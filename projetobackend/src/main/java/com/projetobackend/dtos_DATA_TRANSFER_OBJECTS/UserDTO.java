package com.projetobackend.dtos_DATA_TRANSFER_OBJECTS;

import com.projetobackend.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String primeiroNome, String ultimoNome, BigDecimal balance, String email, String password, UserType userType) {
}
