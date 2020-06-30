package com.backend.todo.service;

import com.backend.todo.domain.Task;
import com.backend.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@Service
public class TaskService {

    private TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return this.taskRepository.findAll();
    }
}
