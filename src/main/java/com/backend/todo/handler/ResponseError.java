package com.backend.todo.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexey Voronin.
 * @since 13.01.2023.
 */
@Getter
public class ResponseError {

    private final HttpStatus httpStatus;
    private final String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime localDateTime = LocalDateTime.now();

    private final Map<String, String> errorMap = new HashMap<>();

    public ResponseError(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
