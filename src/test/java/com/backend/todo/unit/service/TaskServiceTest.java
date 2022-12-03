package com.backend.todo.unit.service;

import com.backend.todo.domain.Category;
import com.backend.todo.domain.Priority;
import com.backend.todo.domain.Task;
import com.backend.todo.dto.TaskCreateEditDto;
import com.backend.todo.dto.TaskReadDto;
import com.backend.todo.mapper.TaskCreateEditMapper;
import com.backend.todo.mapper.TaskReadMapper;
import com.backend.todo.repository.TaskRepository;
import com.backend.todo.service.TaskService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 02.12.2022.
 */
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    private final UUID TASK_ID = UUID.randomUUID();
    private final UUID PRIORITY_ID = UUID.randomUUID();
    private final UUID CATEGORY_ID = UUID.randomUUID();

    private final Task TASK = new Task(
            TASK_ID,
            "test",
            0,
            LocalDate.now(),
            new Priority(PRIORITY_ID, "priority", "green"),
            new Category(CATEGORY_ID, "category", 0, 0));

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskCreateEditMapper taskCreateEditMapper;
    @Mock
    private TaskReadMapper taskReadMapper;

    @InjectMocks
    public TaskService taskService;

    @Test
    public void findAll() {
        when(taskRepository.findAll()).thenReturn(Lists.newArrayList(TASK));

        List<TaskReadDto> result = taskService.findAll();

        assertThat(result).hasSize(1);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void create() {
        when(taskCreateEditMapper.map(any())).thenReturn(TASK);
        when(taskRepository.save(any())).thenReturn(TASK);
        when(taskReadMapper.map(any())).
                thenReturn(new TaskReadDto(TASK_ID, null, 0, null, null, null));

        TaskCreateEditDto taskCreateEditDto =
                new TaskCreateEditDto("test", 0, LocalDate.now(), PRIORITY_ID, CATEGORY_ID);
        TaskReadDto taskReadDto = taskService.create(taskCreateEditDto);

        assertEquals(TASK_ID, taskReadDto.getId());
        verify(taskRepository, times(1)).save(any());
        verify(taskCreateEditMapper, times(1)).map(any());
        verify(taskReadMapper, times(1)).map(any());
    }

    @Test
    public void findById() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(TASK));
        when(taskReadMapper.map(any()))
                .thenReturn(new TaskReadDto(TASK_ID, null, 0, null, null, null));

        Optional<TaskReadDto> actualResult = taskService.findById(TASK_ID);

        actualResult.ifPresent(actual -> assertEquals(TASK_ID, actual.getId()));
        verify(taskRepository, times(1)).findById(any());
        verify(taskReadMapper, times(1)).map(any());
    }

    @Test
    public void deleteWithTheCorrectID() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(TASK));

        boolean actualResult = taskService.deleteTask(TASK_ID);
        assertTrue(actualResult);

        verify(taskRepository, times(1)).findById(any());
        verify(taskRepository, times(1)).delete(any());
    }

    @Test
    public void deleteWithTheInCorrectID() {
        when(taskRepository.findById(any())).thenReturn(Optional.empty());

        boolean actualResult = taskService.deleteTask(TASK_ID);
        assertFalse(actualResult);

        verify(taskRepository, times(1)).findById(any());
    }

    @Test
    public void update() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(TASK));
        when(taskCreateEditMapper.map(any(), any())).thenReturn(TASK);
        when(taskRepository.save(any())).thenReturn(TASK);
        when(taskReadMapper.map(any())).
                thenReturn(new TaskReadDto(TASK_ID, null, 0, null, null, null));

        TaskCreateEditDto taskCreateEditDto =
                new TaskCreateEditDto("test", 0, LocalDate.now(), PRIORITY_ID, CATEGORY_ID);
        Optional<TaskReadDto> actualResult = taskService.update(TASK_ID, taskCreateEditDto);

        actualResult.ifPresent(result -> assertEquals(TASK_ID, result.getId()));
        verify(taskRepository, times(1)).findById(any());
        verify(taskCreateEditMapper, times(1)).map(any(), any());
        verify(taskRepository, times(1)).save(any());
        verify(taskReadMapper, times(1)).map(any());
    }
}
