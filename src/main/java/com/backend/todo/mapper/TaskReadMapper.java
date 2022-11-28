package com.backend.todo.mapper;

import com.backend.todo.domain.Task;
import com.backend.todo.dto.CategoryReadDto;
import com.backend.todo.dto.PriorityReadDto;
import com.backend.todo.dto.TaskReadDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 24.11.2022.
 */
@Component
@AllArgsConstructor
public class TaskReadMapper implements Mapper<Task, TaskReadDto> {

    private final PriorityReadMapper priorityReadMapper;
    private final CategoryReadMapper categoryReadMapper;

    @Override
    public TaskReadDto map(Task object) {
        PriorityReadDto priority = Optional.ofNullable(object.getPriority())
                .map(priorityReadMapper::map).
                        orElse(null);

        CategoryReadDto category = Optional.ofNullable(object.getCategory())
                .map(categoryReadMapper::map).
                        orElse(null);

        return new TaskReadDto(
                object.getId(),
                object.getTitle(),
                object.getCompleted(),
                object.getDate(),
                priority,
                category
        );
    }
}
