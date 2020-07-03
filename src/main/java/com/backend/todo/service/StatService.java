package com.backend.todo.service;

import com.backend.todo.domain.Stat;
import com.backend.todo.repository.StatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alexey Voronin.
 * @since 03.07.2020.
 */
@Service
public class StatService {

    private StatRepository statRepository;

    public StatService(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    public List<Stat> getStats() {
        return this.statRepository.findAll();
    }
}
