package com.backend.todo.unit.controller;

import com.backend.todo.controller.PriorityController;
import com.backend.todo.dto.PriorityCreateEditDto;
import com.backend.todo.dto.PriorityReadDto;
import com.backend.todo.service.PriorityService;
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
@WebMvcTest(PriorityController.class)
@RequiredArgsConstructor
public class PriorityControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private PriorityService priorityService;

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/priorities"))
                .andExpect(status().is2xxSuccessful());

        verify(priorityService, times(1)).findAllByOrderByTitleAsc();
    }

    @Test
    public void createWithTitleAndColor() throws Exception {
        mockMvc.perform(post("/api/v1/priorities")
                .content(new ObjectMapper().writeValueAsString(
                        new PriorityCreateEditDto("title", "color")))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful());

        verify(priorityService, times(1)).create(any());
    }

    @Test
    public void createWithoutTitle() throws Exception {
        mockMvc.perform(post("/api/v1/priorities")
                .content(new ObjectMapper().writeValueAsString(
                        new PriorityCreateEditDto("", "color")))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(priorityService);
    }

    @Test
    public void createWithoutColor() throws Exception {
        mockMvc.perform(post("/api/v1/priorities")
                .content(new ObjectMapper().writeValueAsString(
                        new PriorityCreateEditDto("title", "")))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(priorityService);
    }

    @Test
    public void updateWithTitleAndAndColorAndCorrectId() throws Exception {
        when(priorityService.update(any(), any()))
                .thenReturn(Optional.of(new PriorityReadDto(null, "title", "color")));

        mockMvc.perform(put("/api/v1/priorities/{id}", UUID.randomUUID().toString())
                .content(new ObjectMapper().writeValueAsString(
                        new PriorityCreateEditDto("title", "color")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful());

        verify(priorityService, times(1)).update(any(), any());
    }

    @Test
    public void updateWithoutTitle() throws Exception {
        when(priorityService.update(any(), any()))
                .thenReturn(Optional.of(new PriorityReadDto(null, "title", "color")));

        mockMvc.perform(put("/api/v1/priorities/{id}", UUID.randomUUID().toString())
                .content(new ObjectMapper().writeValueAsString(
                        new PriorityCreateEditDto("", "color")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        verifyNoMoreInteractions(priorityService);
    }

    @Test
    public void updateWithoutColor() throws Exception {
        when(priorityService.update(any(), any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/priorities/{id}", UUID.randomUUID().toString())
                .content(new ObjectMapper().writeValueAsString(
                        new PriorityCreateEditDto("title", "")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

        verifyNoMoreInteractions(priorityService);
    }

    @Test
    public void updateWithTitleAndColorAndIncorrectId() throws Exception {
        when(priorityService.update(any(), any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/priorities/{id}", UUID.randomUUID().toString())
                .content(new ObjectMapper().writeValueAsString(
                        new PriorityCreateEditDto("title", "color")))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound());

        verify(priorityService, times(1)).update(any(), any());
    }

    // TODO: 10.12.2022 11111111111111111111111111111111111111111

    @Test
    public void findByIdWithCorrectId() throws Exception {
        when(priorityService.findById(any()))
                .thenReturn(Optional.of(new PriorityReadDto(null, "title", "color")));

        mockMvc.perform(get("/api/v1/priorities/{id}", UUID.randomUUID().toString()))
                .andExpect(status().is2xxSuccessful());

        verify(priorityService, times(1)).findById(any());
    }

    @Test
    public void findByIdWithIncorrectId() throws Exception {
        when(priorityService.findById(any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/priorities/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

        verify(priorityService, times(1)).findById(any());
    }

    @Test
    public void deleteWithCorrectId() throws Exception {
        when(priorityService.delete(any())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/priorities/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNoContent());

        verify(priorityService, times(1)).delete(any());
    }

    @Test
    public void deleteWithIncorrectId() throws Exception {
        when(priorityService.delete(any())).thenReturn(false);
        mockMvc.perform(delete("/api/v1/priorities/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

        verify(priorityService, times(1)).delete(any());
    }
}
