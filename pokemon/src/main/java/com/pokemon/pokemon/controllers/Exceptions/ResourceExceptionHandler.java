package com.pokemon.pokemon.controllers.Exceptions;

import com.pokemon.pokemon.dto.DefaultError;
import org.apache.logging.log4j.util.InternalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<DefaultError> entityNotFound() {
        DefaultError error = new DefaultError(Instant.now(), "Resource not found on server.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<DefaultError> internalError() {
        DefaultError error = new DefaultError(Instant.now(), "An error occurred, contact the admin.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
