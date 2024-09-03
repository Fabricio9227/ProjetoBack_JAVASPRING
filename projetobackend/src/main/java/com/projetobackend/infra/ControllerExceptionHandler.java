package com.projetobackend.infra;


import com.projetobackend.dtos_DATA_TRANSFER_OBJECTS.ExceptionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity DadosJaExistentes(DataIntegrityViolationException erro) {
        ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário já cadastrado", "400");
        return ResponseEntity.badRequest().body(exceptionDTO);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity Erro404(EntityNotFoundException erro) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity ErroGenerico(Exception erro) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(erro.getMessage(), "500");
        return ResponseEntity.internalServerError().body(exceptionDTO);
    }
}
