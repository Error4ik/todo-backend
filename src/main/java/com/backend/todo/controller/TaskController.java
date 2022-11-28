package com.backend.todo.controller;

import com.backend.todo.dto.TaskCreateEditDto;
import com.backend.todo.dto.TaskReadDto;
import com.backend.todo.search.SearchParams;
import com.backend.todo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    private Logger logger = LoggerFactory.getLogger(TaskController.class);

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping("/tasks")
    public List<TaskReadDto> getTasks() {
        return this.taskService.findAll();
    }

    @RequestMapping("/add")
    public ResponseEntity<TaskReadDto> create(@RequestBody TaskCreateEditDto taskCreateEditDto) {
        return ok(taskService.create(taskCreateEditDto));
    }

    @RequestMapping("/{id}")
    public ResponseEntity<TaskReadDto> updateTask(@PathVariable("id") UUID id, @RequestBody TaskCreateEditDto taskCreateEditDto) {
        logger.info(String.format("Input arguments: %s", taskCreateEditDto));
        if (id == null) {
            logger.info("Missed parameter: id must not be null");
            return new ResponseEntity("Missed parameter: id must not be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (taskCreateEditDto.getTitle() == null || taskCreateEditDto.getTitle().trim().isEmpty()) {
            logger.info("Missed parameters: title");
            return new ResponseEntity("Missed parameter: title", HttpStatus.NOT_ACCEPTABLE);
        }
        TaskReadDto taskReadDto = this.taskService.updateTask(id, taskCreateEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        logger.info(String.format("Update: %s", taskReadDto));
        return ok(taskReadDto);
    }

    @RequestMapping("/id/{id}")
    public ResponseEntity<TaskReadDto> getById(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        TaskReadDto taskReadDto = this.taskService.getTaskById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        logger.info(String.format("Return: %s", taskReadDto));
        return ok(taskReadDto);
    }

    @RequestMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return taskService.deleteTask(id)
                ? noContent().build()
                : notFound().build();
    }

    @RequestMapping("/search")
    public ResponseEntity<Page<TaskReadDto>> searchTasks(@RequestBody @NonNull SearchParams searchParams) {
        return ok(taskService.searchTasks(searchParams));
    }
}
