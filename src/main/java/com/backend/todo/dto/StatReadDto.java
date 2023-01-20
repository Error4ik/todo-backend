package com.backend.todo.dto;

import lombok.Value;

/**
 * @author Alexey Voronin.
 * @since 28.11.2022.
 */
@Value
public class StatReadDto {

    int completedTotal;
    int uncompletedTotal;
}
