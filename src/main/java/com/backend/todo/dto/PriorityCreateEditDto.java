package com.backend.todo.dto;

import lombok.Value;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 27.11.2022.
 */
@Value
public class PriorityCreateEditDto {

    String title;
    String color;
}
