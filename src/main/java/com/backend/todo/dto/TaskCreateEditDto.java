package com.backend.todo.dto;

import lombok.Value;

import java.sql.Timestamp;
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
    Timestamp date;
    UUID priorityId;
    UUID categoryId;
}
