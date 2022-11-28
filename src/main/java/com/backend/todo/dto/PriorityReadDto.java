package com.backend.todo.dto;

import lombok.Value;

import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 24.11.2022.
 */
@Value
public class PriorityReadDto {

    UUID id;
    String title;
    String color;
}
