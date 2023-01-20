package com.backend.todo.exception;

/**
 * @author Alexey Voronin.
 * @since 14.01.2023.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
