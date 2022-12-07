package com.backend.todo.integration.service;

import com.backend.todo.dto.TaskCreateEditDto;
import com.backend.todo.dto.TaskReadDto;
import com.backend.todo.integration.IntegrationBaseTest;
import com.backend.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
public class TaskServiceIT extends IntegrationBaseTest {

    private final UUID TASK_ID = UUID.fromString("21c257ea-992a-4d12-a155-b52319da02c7");
    private final UUID PRIORITY_ID = UUID.fromString("b6a16ada-9d8a-407d-b4da-271d835156b8");
    private final UUID CATEGORY_ID = UUID.fromString("c4c0efab-4050-4b65-937c-75be61f9eabc");

    private final TaskService taskService;

    @Test
    public void findAll() {
        List<TaskReadDto> actualResult = taskService.findAll();

        assertThat(actualResult).hasSize(5);
    }

    @Test
    public void create() {
        TaskCreateEditDto newTask =
                new TaskCreateEditDto("new task", 0, LocalDate.now(), PRIORITY_ID, CATEGORY_ID);

        TaskReadDto actualValue = taskService.create(newTask);

        assertNotNull(actualValue.getId());
        assertEquals(newTask.getTitle(), actualValue.getTitle());
    }

    @Test
    public void findById() {
        Optional<TaskReadDto> actualResult = taskService.findById(TASK_ID);

        assertTrue(actualResult.isPresent());

        actualResult.ifPresent(actual -> assertEquals("Do gymnastics", actual.getTitle()));
    }

    @Test
    public void delete() {
        assertTrue(taskService.delete(TASK_ID));
        assertFalse(taskService.delete(UUID.randomUUID()));
    }

    @Test
    public void update() {
        TaskCreateEditDto newTask =
                new TaskCreateEditDto("some task", 0, LocalDate.now(), PRIORITY_ID, CATEGORY_ID);

        Optional<TaskReadDto> actualValue = taskService.update(TASK_ID, newTask);

        assertTrue(actualValue.isPresent());
        actualValue.ifPresent(actual -> assertEquals(newTask.getTitle(), actual.getTitle()));
    }
}

