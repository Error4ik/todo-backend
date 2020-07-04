package com.backend.todo.service;

import com.backend.todo.domain.Task;
import com.backend.todo.repository.TaskRepository;
import com.backend.todo.search.SearchParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@Service
@Transactional
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

    public Page<Task> searchTasks(SearchParams param) {
        String sortColumn = param.getSortColumn() == null ||
                param.getSortColumn().trim().equals("") ?
                "title" :
                param.getSortColumn();
        Sort.Direction direction =
                param.getSortDirection() == null || param.getSortDirection().trim().equalsIgnoreCase("asc")
                        ? Sort.Direction.ASC
                        : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(
                param.getPageNumber() == null ? 0 : param.getPageNumber(),
                param.getPageLimit() == null ? 4 : param.getPageLimit(),
                Sort.by(direction, sortColumn));
        return this.taskRepository.searchTaskByParams(
                param.getTitle(),
                param.getCompleted(),
                param.getPriority(),
                param.getCategory(),
                pageRequest);
    }
}
