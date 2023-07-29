package br.com.fiap.pettech.dominio.produto.controller.exception;

import br.com.fiap.pettech.dominio.produto.service.exception.ControllerNotFoundException;
import br.com.fiap.pettech.dominio.produto.service.exception.DatabaseException;
import br.com.fiap.pettech.dominio.produto.service.exception.GenericError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private GenericError error = new GenericError();

    @ExceptionHandler(ControllerNotFoundException.class)
    public ResponseEntity<GenericError> entityNotFound(ControllerNotFoundException exception, HttpServletRequest request) {
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Entidade não encontrada");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.error);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<GenericError> entityNotFound(DatabaseException exception, HttpServletRequest request) {
        error.setTimestamp(Instant.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Entidade não encontrada");
        error.setMessage(exception.getMessage());
        error.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(this.error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidacaoForm> validation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ValidacaoForm validacaoForm = new ValidacaoForm();
        validacaoForm.setTimestamp(Instant.now());
        validacaoForm.setPath(request.getRequestURI());
        validacaoForm.setStatus(HttpStatus.BAD_REQUEST.value());
        validacaoForm.setMessage(exception.getMessage());
        validacaoForm.setError("Erro de validação");

        for (FieldError field: exception.getBindingResult().getFieldErrors()) {
            validacaoForm.addMensagens(field.getField(), field.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validacaoForm);
    }

}
