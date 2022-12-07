package com.backend.todo.integration.service;

import com.backend.todo.dto.CategoryCreateEditDto;
import com.backend.todo.dto.CategoryReadDto;
import com.backend.todo.integration.IntegrationBaseTest;
import com.backend.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 07.12.2022.
 */
@RequiredArgsConstructor
public class CategoryServiceIT extends IntegrationBaseTest {

    private final UUID CATEGORY_ID = UUID.fromString("c4c0efab-4050-4b65-937c-75be61f9eabc");

    private final CategoryService categoryService;

    @Test
    public void findAll() {
        List<CategoryReadDto> actualResult = categoryService.findAllByOrderByTitleAsc();

        assertThat(actualResult).hasSize(3);
    }

    @Test
    public void create() {
        CategoryCreateEditDto newCategory = new CategoryCreateEditDto("new category", 0, 0);

        CategoryReadDto actualValue = categoryService.create(newCategory);

        assertNotNull(actualValue.getId());
        assertEquals(newCategory.getTitle(), actualValue.getTitle());
    }

    @Test
    public void findById() {
        Optional<CategoryReadDto> actualResult = categoryService.findById(CATEGORY_ID);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(actual -> assertEquals("Hobby", actual.getTitle()));
    }

    @Test
    public void delete() {
        CategoryCreateEditDto newCategory = new CategoryCreateEditDto("new category", 0, 0);

        CategoryReadDto actualValue = categoryService.create(newCategory);

        assertNotNull(actualValue.getId());
        assertTrue(categoryService.delete(actualValue.getId()));
        assertFalse(categoryService.delete(UUID.randomUUID()));
    }

    @Test
    public void update() {
        CategoryCreateEditDto newCategory = new CategoryCreateEditDto("new category", 0, 0);

        Optional<CategoryReadDto> actualValue = categoryService.update(CATEGORY_ID, newCategory);

        assertTrue(actualValue.isPresent());
        actualValue.ifPresent(actual -> assertEquals(newCategory.getTitle(), actual.getTitle()));
    }
}
