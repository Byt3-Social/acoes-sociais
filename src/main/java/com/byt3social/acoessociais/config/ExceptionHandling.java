package com.byt3social.acoessociais.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.byt3social.acoessociais.dto.FieldErrorDTO;

import java.util.List;

@RestControllerAdvice
public class ExceptionHandling {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();

        return new ResponseEntity(errors.stream().map(FieldErrorDTO::new).toList(), HttpStatus.BAD_REQUEST);
    }
}
