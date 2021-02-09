package com.online.compiler.exception.controller;

import com.online.compiler.exception.EntityCreateFailedException;
import com.online.compiler.exception.EntityDeleteFailedException;
import com.online.compiler.exception.EntityNotFoundException;
import com.online.compiler.exception.EntityUpdateFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<Object> handleFileNotFoundException(EntityNotFoundException ex) {
        String customEx = "Could not find\n " + ex.getMessage();
        return new ResponseEntity<>(customEx, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EntityCreateFailedException.class)
    public ResponseEntity<Object> handleCreateFailedException(EntityCreateFailedException ex) {
        String customEx = "Could not save\n " + ex.getMessage();
        return new ResponseEntity<>(customEx, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityDeleteFailedException.class)
    public ResponseEntity<Object> handleDeleteFailedException(EntityDeleteFailedException ex) {
        String customEx = "Could not delete\n " + ex.getMessage();
        return new ResponseEntity<>(customEx, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityUpdateFailedException.class)
    public ResponseEntity<Object> handleUpdateFailedException(EntityUpdateFailedException ex) {
        String customEx = "Could not update\n " + ex.getMessage();
        return new ResponseEntity<>(customEx, HttpStatus.BAD_REQUEST);
    }
}
