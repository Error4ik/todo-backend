package com.backend.todo.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 27.11.2022.
 */
@Value
@FieldNameConstants
public class PriorityCreateEditDto {

    String title;
    String color;
}
