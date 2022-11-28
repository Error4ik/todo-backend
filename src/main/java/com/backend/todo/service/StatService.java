package com.backend.todo.service;

import com.backend.todo.dto.StatReadDto;
import com.backend.todo.mapper.StatReadMapper;
import com.backend.todo.repository.StatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alexey Voronin.
 * @since 03.07.2020.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatService {

    private final StatRepository statRepository;
    private final StatReadMapper statReadMapper;


    public List<StatReadDto> getStats() {
        return this.statRepository.findAll().stream().map(statReadMapper::map).collect(Collectors.toList());
    }
}
