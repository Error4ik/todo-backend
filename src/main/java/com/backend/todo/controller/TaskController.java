package com.backend.todo.controller;

import com.backend.todo.dto.TaskCreateEditDto;
import com.backend.todo.dto.TaskReadDto;
import com.backend.todo.exception.NotFoundException;
import com.backend.todo.search.SearchParams;
import com.backend.todo.service.TaskService;
import com.backend.todo.validation.group.CreateTask;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskReadDto> findAll() {
        return this.taskService.findAll();
    }

    @PostMapping
    public ResponseEntity<TaskReadDto> create(
            @RequestBody @Validated({Default.class, CreateTask.class}) TaskCreateEditDto taskCreateEditDto) {
        return new ResponseEntity<>(taskService.create(taskCreateEditDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskReadDto> update(
            @PathVariable("id") UUID id,
            @RequestBody @Validated TaskCreateEditDto taskCreateEditDto) {

        TaskReadDto taskReadDto = this.taskService.update(id, taskCreateEditDto)
                .orElseThrow(() -> new NotFoundException("There is no object with this ID"));
        return ok(taskReadDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskReadDto> findById(@PathVariable UUID id) {
        TaskReadDto taskReadDto = this.taskService.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no object with this ID"));
        return ok(taskReadDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return taskService.delete(id)
                ? noContent().build()
                : notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TaskReadDto>> search(@RequestBody @NonNull SearchParams searchParams) {
        return ok(taskService.search(searchParams));
    }
}
