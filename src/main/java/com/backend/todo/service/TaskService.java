package com.backend.todo.service;

import com.backend.todo.search.SearchParams;
import com.backend.todo.domain.Task;
import com.backend.todo.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Task addTask(Task task) {
        return this.taskRepository.save(task);
    }

    public Optional<Task> getTaskById(UUID id) {
        return this.taskRepository.findById(id);
    }

    public void deleteTask(UUID id) {
        this.taskRepository.deleteById(id);
    }

    public Task updateTask(Task task) {
        return this.taskRepository.save(task);
    }

    public List<Task> searchTasks(SearchParams searchParams) {
        return this.taskRepository.searchTaskByParams(
                searchParams.getTitle(),
                searchParams.getCompleted(),
                searchParams.getPriority(),
                searchParams.getCategory());
    }
}
