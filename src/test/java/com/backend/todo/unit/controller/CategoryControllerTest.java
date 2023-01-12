package com.backend.todo.unit.controller;

import com.backend.todo.controller.CategoryController;
import com.backend.todo.dto.CategoryCreateEditDto;
import com.backend.todo.dto.CategoryReadDto;
import com.backend.todo.service.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 09.12.2022.
 */
@WebMvcTest(CategoryController.class)
@RequiredArgsConstructor
public class CategoryControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().is2xxSuccessful());

        verify(categoryService, times(1)).findAllByOrderByTitleAsc();
    }

    @Test
    public void createWithTitle() throws Exception {
        mockMvc.perform(post("/api/v1/categories")
                .content(new ObjectMapper().writeValueAsString(
                        new CategoryCreateEditDto("title", 0, 0)))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful());

        verify(categoryService, times(1)).create(any());
    }

    @Test
    public void createWithoutTitle() throws Exception {
        mockMvc.perform(post("/api/v1/categories")
                .content(new ObjectMapper().writeValueAsString(
                        new CategoryCreateEditDto("", 0, 0)))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateWithTitleAndCorrectId() throws Exception {
        when(categoryService.update(any(), any()))
                .thenReturn(Optional.of(new CategoryReadDto(null, "", 0, 0)));

        mockMvc.perform(put("/api/v1/categories/{id}", UUID.randomUUID().toString())
                .content(new ObjectMapper().writeValueAsString(
                        new CategoryCreateEditDto("title", 0, 0)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

        verify(categoryService, times(1)).update(any(), any());
    }

    @Test
    public void updateWithoutTitleAndCorrectId() throws Exception {
        when(categoryService.update(any(), any()))
                .thenReturn(Optional.of(new CategoryReadDto(null, "", 0, 0)));

        mockMvc.perform(put("/api/v1/categories/{id}", UUID.randomUUID().toString())
                .content(new ObjectMapper().writeValueAsString(
                        new CategoryCreateEditDto("", 0, 0)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void updateWithTitleAndIncorrectId() throws Exception {
        when(categoryService.update(any(), any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/categories/{id}", UUID.randomUUID().toString())
                .content(new ObjectMapper().writeValueAsString(
                        new CategoryCreateEditDto("title", 0, 0)))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

        verify(categoryService, times(1)).update(any(), any());
    }

    @Test
    public void findByIdWithCorrectlyId() throws Exception {
        when(categoryService.findById(any()))
                .thenReturn(Optional.of(new CategoryReadDto(null, "", 0, 0)));

        mockMvc.perform(get("/api/v1/categories/{id}", UUID.randomUUID().toString()))
                .andExpect(status().is2xxSuccessful());

        verify(categoryService, times(1)).findById(any());
    }

    @Test
    public void findByIdWithIncorrectId() throws Exception {
        when(categoryService.findById(any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/categories/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).findById(any());
    }

    @Test
    public void deleteWithCorrectId() throws Exception {
        when(categoryService.delete(any())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/categories/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).delete(any());
    }

    @Test
    public void deleteWithIncorrectId() throws Exception {
        when(categoryService.delete(any())).thenReturn(false);
        mockMvc.perform(delete("/api/v1/categories/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).delete(any());
    }

    @Test
    public void findByTitle() throws Exception {
        mockMvc.perform(get("/api/v1/categories/find-by-title")
                .param("title", "some title"))
                .andExpect(status().is2xxSuccessful());

        verify(categoryService, times(1)).findByTitle(any());
    }
}
