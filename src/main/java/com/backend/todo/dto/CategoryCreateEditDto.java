package com.backend.todo.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 28.11.2022.
 */
@Value
@FieldNameConstants
public class CategoryCreateEditDto {

    String title;
    int completedCount;
    int uncompletedCount;
}
