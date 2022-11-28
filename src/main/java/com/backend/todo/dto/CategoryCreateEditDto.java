package com.backend.todo.dto;

import lombok.Value;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 28.11.2022.
 */
@Value
public class CategoryCreateEditDto {

    String title;
    int completedCount;
    int uncompletedCount;
}