package com.backend.todo.service;

import com.backend.todo.dto.TaskCreateEditDto;
import com.backend.todo.dto.TaskReadDto;
import com.backend.todo.mapper.TaskCreateEditMapper;
import com.backend.todo.mapper.TaskReadMapper;
import com.backend.todo.repository.TaskRepository;
import com.backend.todo.search.SearchParams;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskCreateEditMapper taskCreateEditMapper;
    private final TaskReadMapper taskReadMapper;

    public List<TaskReadDto> findAll() {
        return taskRepository.findAll()
                .stream()
                .map(taskReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public TaskReadDto create(TaskCreateEditDto taskCreateEditDto) {
        return Optional.of(taskCreateEditDto)
                .map(taskCreateEditMapper::map)
                .map(taskRepository::save)
                .map(taskReadMapper::map)
                .orElseThrow();
    }

    public Optional<TaskReadDto> getTaskById(UUID id) {
        return taskRepository.findById(id)
                .map(taskReadMapper::map);
    }

    @Transactional
    public boolean deleteTask(UUID id) {
        return taskRepository.findById(id)
                .map(entity -> {
                    taskRepository.delete(entity);
                    taskRepository.flush();
                    return true;
                })
                .orElse(false);
    }

    @Transactional
    public Optional<TaskReadDto> updateTask(UUID id, TaskCreateEditDto taskCreateEditDto) {
        return taskRepository.findById(id)
                .map(entity -> taskCreateEditMapper.map(taskCreateEditDto, entity))
                .map(taskRepository::save)
                .map(taskReadMapper::map);
    }

    public Page<TaskReadDto> searchTasks(SearchParams param) {
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
        return taskRepository.searchTaskByParams(
                param.getTitle(),
                param.getCompleted(),
                param.getPriority(),
                param.getCategory(),
                pageRequest)
                .map(taskReadMapper::map);
    }
}
