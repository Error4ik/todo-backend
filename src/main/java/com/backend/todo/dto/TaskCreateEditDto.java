package com.backend.todo.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 14.11.2022.
 */
@Value
@FieldNameConstants
public class TaskCreateEditDto {

    String title;
    int completed;
    LocalDate date;
    UUID priorityId;
    UUID categoryId;
}
