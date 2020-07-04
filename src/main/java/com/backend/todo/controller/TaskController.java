package com.backend.todo.controller;

import com.backend.todo.search.SearchParams;
import com.backend.todo.domain.Task;
import com.backend.todo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    private Logger logger = LoggerFactory.getLogger(TaskController.class);

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping("/tasks")
    public List<Task> getTasks() {
        return this.taskService.getTasks();
    }

    @PostMapping("/add")
    public ResponseEntity<Task> addTask(@RequestBody @NonNull Task task) {
        logger.info(String.format("Input arguments: %s", task));
        if (task.getId() != null) {
            logger.info("The redundant parameter: id must be null");
            return new ResponseEntity("The redundant parameter: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            logger.info("Missed parameters: title");
            return new ResponseEntity("Missed parameter: title", HttpStatus.NOT_ACCEPTABLE);
        }
        Task t = this.taskService.addTask(task);
        logger.info(String.format("Save: %s", t));
        return ResponseEntity.ok(t);
    }

    @PostMapping("/update")
    public ResponseEntity updateTask(@RequestBody @NonNull Task task) {
        logger.info(String.format("Input arguments: %s", task));
        if (task.getId() == null) {
            logger.info("Missed parameter: id must not be null");
            return new ResponseEntity("Missed parameter: id must not be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            logger.info("Missed parameters: title");
            return new ResponseEntity("Missed parameter: title", HttpStatus.NOT_ACCEPTABLE);
        }
        Task t = this.taskService.updateTask(task);
        logger.info(String.format("Update: %s", t));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        Optional<Task> task = this.taskService.getTaskById(id);
        if (!task.isPresent()) {
            logger.info("There is no entity with this ID!");
            return new ResponseEntity("There is no entity with this ID!", HttpStatus.NOT_FOUND);
        }
        logger.info(String.format("Return: %s", task.get()));
        return ResponseEntity.ok(task.get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteTask(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        try {
            this.taskService.deleteTask(id);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("There is no entity with this ID!");
        }
        logger.info(String.format("Task was deleted: %s", id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestBody @NonNull SearchParams searchParams) {
        return ResponseEntity.ok(this.taskService.searchTasks(searchParams));
    }
}
