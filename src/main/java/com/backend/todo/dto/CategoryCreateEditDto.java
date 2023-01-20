package com.backend.todo.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alexey Voronin.
 * @since 28.11.2022.
 */
@Value
@FieldNameConstants
public class CategoryCreateEditDto {

    @Size(min = 3, max = 200)
    @NotBlank
    String title;
    int completedCount;
    int uncompletedCount;
}
