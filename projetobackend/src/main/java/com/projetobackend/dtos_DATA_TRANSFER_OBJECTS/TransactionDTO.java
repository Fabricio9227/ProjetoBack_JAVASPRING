package com.projetobackend.dtos_DATA_TRANSFER_OBJECTS;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Integer senderID, Integer receiverID) {
}
