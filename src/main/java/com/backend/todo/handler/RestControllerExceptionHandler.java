package com.backend.todo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Alexey Voronin.
 * @since 13.01.2023.
 */
@Slf4j
@RestControllerAdvice
public class RestControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ResponseError handleException(MethodArgumentNotValidException ex) {
        log.error("ex.getMessage()", ex);
        ResponseError responseError = new ResponseError(HttpStatus.BAD_REQUEST, "Validation failed for argument.");
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            responseError.getErrorMap().put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return responseError;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public ResponseError handleException(ResponseStatusException ex) {
        log.error("ex.getMessage()", ex);
        return new ResponseError(HttpStatus.NOT_FOUND, "There is no object with this ID");
    }
}
