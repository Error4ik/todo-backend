package com.backend.todo.mapper;

import com.backend.todo.domain.Priority;
import com.backend.todo.dto.PriorityReadDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 26.11.2022.
 */
@Component
@AllArgsConstructor
public class PriorityReadMapper implements Mapper<Priority, PriorityReadDto> {
    @Override
    public PriorityReadDto map(Priority object) {
        return new PriorityReadDto(
                object.getId(),
                object.getTitle(),
                object.getColor());
    }
}
