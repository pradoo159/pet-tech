package br.com.fiap.pettech.dominio.produto.controller.exception;

import br.com.fiap.pettech.dominio.produto.service.exception.ControllerNotFoundException;
import br.com.fiap.pettech.dominio.produto.service.exception.DatabaseException;
import br.com.fiap.pettech.dominio.produto.service.exception.GenericError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private GenericError error = new GenericError();

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<GenericError> entityNotFound(ControllerNotFoundException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entidade não encontrada");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(this.error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<GenericError> entityNotFound(DatabaseException exception, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        error.setTimestamp(Instant.now());
        error.setStatus(status.value());
        error.setError("Entidade não encontrada");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(this.error);
    }

}
