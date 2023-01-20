package com.backend.todo.mapper;

import com.backend.todo.domain.Category;
import com.backend.todo.domain.Priority;
import com.backend.todo.domain.Task;
import com.backend.todo.dto.TaskCreateEditDto;
import com.backend.todo.repository.CategoryRepository;
import com.backend.todo.repository.PriorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 24.11.2022.
 */
@Component
@AllArgsConstructor
public class TaskCreateEditMapper implements Mapper<TaskCreateEditDto, Task> {

    private final PriorityRepository priorityRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Task map(TaskCreateEditDto object) {
        Task task = new Task();
        copy(object, task);
        return task;
    }

    @Override
    public Task map(TaskCreateEditDto fromObject, Task toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(TaskCreateEditDto object, Task task) {
        task.setTitle(object.getTitle());
        task.setCompleted(object.getCompleted());
        task.setDate(object.getDate());
        task.setPriority(getPriorityById(object.getPriorityId()));
        task.setCategory(getCategoryById(object.getCategoryId()));
    }

    private Priority getPriorityById(UUID priorityId) {
        return Optional.ofNullable(priorityId)
                .flatMap(priorityRepository::findById)
                .orElse(null);
    }

    private Category getCategoryById(UUID categoryId) {
        return Optional.ofNullable(categoryId)
                .flatMap(categoryRepository::findById)
                .orElse(null);
    }
}
