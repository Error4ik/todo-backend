package com.backend.todo.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author Alexey Voronin.
 * @since 27.11.2022.
 */
@Value
@FieldNameConstants
public class PriorityCreateEditDto {

    @NotBlank
    @Size(min = 3, max = 200)
    String title;

    @NotBlank
    @Size(min = 3, max = 200)
    String color;
}
