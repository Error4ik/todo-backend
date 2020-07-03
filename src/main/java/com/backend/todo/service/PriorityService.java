package com.backend.todo.service;

import com.backend.todo.domain.Priority;
import com.backend.todo.repository.PriorityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<Priority> findAllByOrderByTitleAsc() {
        return this.priorityRepository.findAllByOrderByTitleAsc();
    }

    public Priority addPriority(Priority priority) {
        return this.priorityRepository.save(priority);
    }

    public Priority updatePriority(Priority priority) {
        return this.priorityRepository.save(priority);
    }

    public Optional<Priority> getPriorityById(UUID id) {
        return this.priorityRepository.findById(id);
    }

    public void deletePriority(UUID id) {
        this.priorityRepository.deleteById(id);
    }
}
