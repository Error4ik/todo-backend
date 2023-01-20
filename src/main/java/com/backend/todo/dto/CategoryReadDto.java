package com.backend.todo.dto;

import lombok.Value;

import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 24.11.2022.
 */
@Value
public class CategoryReadDto {

    UUID id;
    String title;
    int completedCount;
    int uncompletedCount;
}
