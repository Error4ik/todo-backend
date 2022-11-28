package com.backend.todo.service;

import com.backend.todo.dto.PriorityCreateEditDto;
import com.backend.todo.dto.PriorityReadDto;
import com.backend.todo.mapper.PriorityCreateEditMapper;
import com.backend.todo.mapper.PriorityReadMapper;
import com.backend.todo.repository.PriorityRepository;
import lombok.RequiredArgsConstructor;
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
public class PriorityService {

    private final PriorityRepository priorityRepository;
    private final PriorityReadMapper priorityReadMapper;
    private final PriorityCreateEditMapper priorityCreateEditMapper;

    public List<PriorityReadDto> findAllByOrderByTitleAsc() {
        return priorityRepository.findAllByOrderByTitleAsc()
                .stream()
                .map(priorityReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public PriorityReadDto create(PriorityCreateEditDto priorityCreateEditDto) {
        return Optional.of(priorityCreateEditDto)
                .map(priorityCreateEditMapper::map)
                .map(priorityRepository::save)
                .map(priorityReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<PriorityReadDto> update(UUID id, PriorityCreateEditDto priorityCreateEditDto) {
        return priorityRepository.findById(id)
                .map(entity -> priorityCreateEditMapper.map(priorityCreateEditDto, entity))
                .map(priorityRepository::save)
                .map(priorityReadMapper::map);
    }

    public Optional<PriorityReadDto> getPriorityById(UUID id) {
        return priorityRepository.findById(id)
                .map(priorityReadMapper::map);
    }

    @Transactional
    public boolean deletePriority(UUID id) {
        return priorityRepository.findById(id)
                .map(entity -> {
                    priorityRepository.delete(entity);
                    priorityRepository.flush();
                    return true;
                }).orElse(false);
    }
}
