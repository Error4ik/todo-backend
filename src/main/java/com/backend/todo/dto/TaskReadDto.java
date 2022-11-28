package com.backend.todo.dto;

import lombok.Value;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 24.11.2022.
 */
@Value
public class TaskReadDto {

    UUID id;
    String title;
    int completed;
    Timestamp date;
    PriorityReadDto priority;
    CategoryReadDto category;
}
