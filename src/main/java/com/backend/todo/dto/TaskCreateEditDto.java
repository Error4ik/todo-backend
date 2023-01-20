package com.backend.todo.dto;

import com.backend.todo.validation.group.CreateTask;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 14.11.2022.
 */
@Value
@FieldNameConstants
public class TaskCreateEditDto {

    @NotBlank
    @Size(min = 3, max = 200)
    String title;

    int completed;

    @Future(groups = {CreateTask.class})
    LocalDate date;

    @NotNull
    UUID priorityId;

    @NotNull
    UUID categoryId;
}
