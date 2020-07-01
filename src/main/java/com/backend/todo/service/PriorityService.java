package com.backend.todo.service;

import com.backend.todo.domain.Priority;
import com.backend.todo.repository.PriorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@Service
public class PriorityService {

    private PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public List<Priority> getPriorities() {
        return this.priorityRepository.findAll();
    }

    public Priority addPriority(Priority priority) {
        return this.priorityRepository.save(priority);
    }
}
