package com.backend.todo.unit.service;

import com.backend.todo.domain.Category;
import com.backend.todo.dto.CategoryCreateEditDto;
import com.backend.todo.dto.CategoryReadDto;
import com.backend.todo.mapper.CategoryCreateEditMapper;
import com.backend.todo.mapper.CategoryReadMapper;
import com.backend.todo.repository.CategoryRepository;
import com.backend.todo.service.CategoryService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Alexey Voronin.
 * @since 03.12.2022.
 */
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    private final UUID CATEGORY_ID = UUID.randomUUID();

    private final Category CATEGORY = new Category(
            CATEGORY_ID,
            "category",
            0,
            0
    );

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryReadMapper categoryReadMapper;
    @Mock
    private CategoryCreateEditMapper categoryCreateEditMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    public void findAll() {
        when(categoryRepository.findAllByOrderByTitleAsc()).thenReturn(Lists.newArrayList(CATEGORY));

        List<CategoryReadDto> result = categoryService.findAllByOrderByTitleAsc();

        assertThat(result).hasSize(1);
        verify(categoryRepository, times(1)).findAllByOrderByTitleAsc();
    }

    @Test
    public void create() {
        when(categoryCreateEditMapper.map(any())).thenReturn(CATEGORY);
        when(categoryRepository.save(any())).thenReturn(CATEGORY);
        when(categoryReadMapper.map(any())).
                thenReturn(new CategoryReadDto(CATEGORY_ID, "test", 0, 0));

        CategoryCreateEditDto categoryCreateEditDto =
                new CategoryCreateEditDto("test", 0, 0);
        CategoryReadDto categoryReadDto = categoryService.create(categoryCreateEditDto);

        assertEquals(CATEGORY_ID, categoryReadDto.getId());
        verify(categoryCreateEditMapper, times(1)).map(any());
        verify(categoryRepository, times(1)).save(any());
        verify(categoryReadMapper, times(1)).map(any());
    }

    @Test
    public void findById() {
        when(categoryRepository.findById(any())).thenReturn(Optional.of(CATEGORY));
        when(categoryReadMapper.map(any()))
                .thenReturn(new CategoryReadDto(CATEGORY_ID, "test", 0, 0));

        Optional<CategoryReadDto> actualResult = categoryService.findById(CATEGORY_ID);

        actualResult.ifPresent(actual -> assertEquals(CATEGORY_ID, actual.getId()));
        verify(categoryRepository, times(1)).findById(any());
        verify(categoryReadMapper, times(1)).map(any());
    }

    @Test
    public void deleteWithTheCorrectID() {
        when(categoryRepository.findById(any())).thenReturn(Optional.of(CATEGORY));

        boolean actualResult = categoryService.delete(CATEGORY_ID);
        assertTrue(actualResult);

        verify(categoryRepository, times(1)).findById(any());
        verify(categoryRepository, times(1)).delete(any());
    }

    @Test
    public void deleteWithTheInCorrectID() {
        when(categoryRepository.findById(any())).thenReturn(Optional.empty());

        boolean actualResult = categoryService.delete(CATEGORY_ID);
        assertFalse(actualResult);

        verify(categoryRepository, times(1)).findById(any());
    }

    @Test
    public void update() {
        when(categoryRepository.findById(any())).thenReturn(Optional.of(CATEGORY));
        when(categoryCreateEditMapper.map(any(), any())).thenReturn(CATEGORY);
        when(categoryRepository.saveAndFlush(any())).thenReturn(CATEGORY);
        when(categoryReadMapper.map(any())).
                thenReturn(new CategoryReadDto(CATEGORY_ID, "test", 0, 0));

        CategoryCreateEditDto taskCreateEditDto =
                new CategoryCreateEditDto("test", 0, 0);
        Optional<CategoryReadDto> actualResult = categoryService.update(CATEGORY_ID, taskCreateEditDto);

        actualResult.ifPresent(result -> assertEquals(CATEGORY_ID, result.getId()));
        verify(categoryRepository, times(1)).findById(any());
        verify(categoryCreateEditMapper, times(1)).map(any(), any());
        verify(categoryRepository, times(1)).saveAndFlush(any());
        verify(categoryReadMapper, times(1)).map(any());
    }

    @Test
    public void findByTitle() {
        when(categoryRepository.findByTitle(any())).thenReturn(Lists.newArrayList(CATEGORY));

        List<CategoryReadDto> result = categoryService.findByTitle(any());

        assertThat(result).hasSize(1);
        verify(categoryRepository, times(1)).findByTitle(any());
    }
}
