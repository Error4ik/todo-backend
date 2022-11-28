package com.backend.todo.mapper;

import com.backend.todo.domain.Category;
import com.backend.todo.dto.CategoryCreateEditDto;
import org.springframework.stereotype.Component;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 28.11.2022.
 */
@Component
public class CategoryCreateEditMapper implements Mapper<CategoryCreateEditDto, Category> {

    @Override
    public Category map(CategoryCreateEditDto object) {
        Category category = new Category();
        copy(object, category);
        return category;
    }

    @Override
    public Category map(CategoryCreateEditDto fromObject, Category toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(CategoryCreateEditDto categoryCreateEditDto, Category category) {
        category.setTitle(categoryCreateEditDto.getTitle());
        category.setCompletedCount(categoryCreateEditDto.getCompletedCount());
        category.setUncompletedCount(categoryCreateEditDto.getUncompletedCount());
    }
}
