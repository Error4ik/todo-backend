package com.backend.todo.controller;

import com.backend.todo.domain.Task;
import com.backend.todo.service.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping("/tasks")
    public List<Task> getTasks() {
        return this.taskService.getTasks();
    }
}
