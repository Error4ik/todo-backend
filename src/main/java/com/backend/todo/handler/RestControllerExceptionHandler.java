package com.backend.todo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Alexey Voronin.
 * @since 13.01.2023.
 */
@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error("ex.getMessage()", ex);
        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, "Validation failed for argument.");
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                responseError.getErrorMap().put(fieldError.getField(), fieldError.getDefaultMessage()));

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleNotFound(ResponseStatusException ex) {
        log.error("ex.getMessage()", ex);
        return new ResponseEntity<>(
                new ResponseError(HttpStatus.NOT_FOUND, "There is no object with this ID"),
                HttpStatus.NOT_FOUND);
    }
}
