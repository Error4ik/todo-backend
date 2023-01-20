package com.backend.todo.mapper;

import com.backend.todo.domain.Stat;
import com.backend.todo.dto.StatReadDto;
import org.springframework.stereotype.Component;

/**
 * @author Alexey Voronin.
 * @since 28.11.2022.
 */
@Component
public class StatReadMapper implements Mapper<Stat, StatReadDto> {

    @Override
    public StatReadDto map(Stat object) {
        return new StatReadDto(object.getCompletedTotal(), object.getUncompletedTotal());
    }
}
