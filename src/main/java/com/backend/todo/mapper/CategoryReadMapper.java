package com.backend.todo.mapper;

import com.backend.todo.domain.Category;
import com.backend.todo.dto.CategoryReadDto;
import org.springframework.stereotype.Component;

/**
 * @author Alexey Voronin.
 * @since 26.11.2022.
 */
@Component
public class CategoryReadMapper implements Mapper<Category, CategoryReadDto> {

    @Override
    public CategoryReadDto map(Category object) {
        return new CategoryReadDto(
                object.getId(),
                object.getTitle(),
                object.getCompletedCount(),
                object.getUncompletedCount());
    }
}
