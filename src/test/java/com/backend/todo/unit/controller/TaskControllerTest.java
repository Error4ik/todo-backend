package com.backend.todo.unit.controller;

import com.backend.todo.controller.TaskController;
import com.backend.todo.dto.TaskCreateEditDto;
import com.backend.todo.dto.TaskReadDto;
import com.backend.todo.search.SearchParams;
import com.backend.todo.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexey Voronin.
 * @since 10.12.2022.
 */
@WebMvcTest(TaskController.class)
@RequiredArgsConstructor
public class TaskControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/tasks"))
                .andExpect(status().is2xxSuccessful());

        verify(taskService, times(1)).findAll();
    }

    @Test
    public void create() throws Exception {
        String string = new ObjectMapper().writeValueAsString(
                new TaskCreateEditDto("title", 0, LocalDate.now(), UUID.randomUUID(), UUID.randomUUID()));
        mockMvc.perform(post("/api/v1/tasks")
                .content(new ObjectMapper().writeValueAsString(
                        new TaskCreateEditDto("title", 0, null, UUID.randomUUID(), UUID.randomUUID())))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful());

        verify(taskService, times(1)).create(any());
    }

    @Test
    public void updateWithoutTitle() throws Exception {
        mockMvc.perform(put("/api/v1/tasks/{id}", UUID.randomUUID().toString())
                .content(new ObjectMapper().writeValueAsString(
                        new TaskCreateEditDto("", 0, null, null, null)))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(taskService);
    }

    @Test
    public void updateWithTitle() throws Exception {
        when(taskService.update(any(), any()))
                .thenReturn(Optional.of(
                        new TaskReadDto(UUID.randomUUID(), "title", 0, null, null, null)));

        mockMvc.perform(put("/api/v1/tasks/{id}", UUID.randomUUID().toString())
                .content(new ObjectMapper().writeValueAsString(
                        new TaskCreateEditDto("Title", 0, null, UUID.randomUUID(), UUID.randomUUID())))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful());

        verify(taskService, times(1)).update(any(), any());
    }

    @Test
    public void updateWithIncorrectId() throws Exception {
        when(taskService.update(any(), any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/tasks/{id}", UUID.randomUUID().toString())
                .content(new ObjectMapper().writeValueAsString(
                        new TaskCreateEditDto("Title", 0, null, null, null)))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(taskService);
    }

    @Test
    public void findByIdWithCorrectId() throws Exception {
        when(taskService.findById(any()))
                .thenReturn(Optional.of(
                        new TaskReadDto(UUID.randomUUID(), "", 0, null, null, null)));

        mockMvc.perform(get("/api/v1/tasks/{id}", UUID.randomUUID().toString()))
                .andExpect(status().is2xxSuccessful());

        verify(taskService, times(1)).findById(any());
    }

    @Test
    public void findByIdWithIncorrectId() throws Exception {
        when(taskService.findById(any()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/tasks/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).findById(any());
    }

    @Test
    public void deleteWithCorrectId() throws Exception {
        when(taskService.delete(any())).thenReturn(true);

        mockMvc.perform(delete("/api/v1/tasks/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNoContent());

        verify(taskService, times(1)).delete(any());
    }

    @Test
    public void deleteWithIncorrectId() throws Exception {
        when(taskService.delete(any())).thenReturn(false);

        mockMvc.perform(delete("/api/v1/tasks/{id}", UUID.randomUUID().toString()))
                .andExpect(status().isNotFound());

        verify(taskService, times(1)).delete(any());
    }

    @Test
    public void search() throws Exception {
        mockMvc.perform(get("/api/v1/tasks/search")
                .content(new ObjectMapper().writeValueAsString(new SearchParams()))
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().is2xxSuccessful());

        verify(taskService, times(1)).search(any());
    }
}
