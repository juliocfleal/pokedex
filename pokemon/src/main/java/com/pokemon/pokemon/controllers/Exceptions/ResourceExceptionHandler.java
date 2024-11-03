package com.pokemon.pokemon.controllers.Exceptions;

import com.pokemon.pokemon.dto.DefaultError;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.InvalidParameterException;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DefaultError> entityNotFound(IllegalArgumentException illegalArgumentException) {
        DefaultError error = new DefaultError(Instant.now(), illegalArgumentException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<DefaultError> internalError() {
        DefaultError error = new DefaultError(Instant.now(), "An error occurred, contact the admin.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<DefaultError> RuntimeError(RuntimeException errorMessage, HttpServletRequest request) {
        DefaultError error = new DefaultError(Instant.now(), errorMessage.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
