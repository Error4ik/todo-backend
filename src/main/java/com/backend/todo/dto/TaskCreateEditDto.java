package com.backend.todo.dto;

import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 14.11.2022.
 */
@Value
public class TaskCreateEditDto {

    String title;
    int completed;
    LocalDate date;
    UUID priorityId;
    UUID categoryId;
}
