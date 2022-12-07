package com.backend.todo.service;

import com.backend.todo.dto.CategoryCreateEditDto;
import com.backend.todo.dto.CategoryReadDto;
import com.backend.todo.mapper.CategoryCreateEditMapper;
import com.backend.todo.mapper.CategoryReadMapper;
import com.backend.todo.repository.CategoryRepository;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryReadMapper categoryReadMapper;
    private final CategoryCreateEditMapper categoryCreateEditMapper;

    public List<CategoryReadDto> findAllByOrderByTitleAsc() {
        return categoryRepository.findAllByOrderByTitleAsc()
                .stream()
                .map(categoryReadMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryReadDto create(CategoryCreateEditDto categoryCreateEditDto) {
        return Optional.of(categoryCreateEditDto)
                .map(categoryCreateEditMapper::map)
                .map(categoryRepository::save)
                .map(categoryReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<CategoryReadDto> update(UUID id, CategoryCreateEditDto categoryCreateEditDto) {
        return categoryRepository.findById(id)
                .map(entity -> categoryCreateEditMapper.map(categoryCreateEditDto, entity))
                .map(categoryRepository::saveAndFlush)
                .map(categoryReadMapper::map);
    }

    public Optional<CategoryReadDto> findById(UUID id) {
        return categoryRepository.findById(id)
                .map(categoryReadMapper::map);
    }

    @Transactional
    public boolean delete(UUID id) {
        return categoryRepository.findById(id)
                .map(entity -> {
                    categoryRepository.delete(entity);
                    categoryRepository.flush();
                    return true;
                }).orElse(false);
    }

    public List<CategoryReadDto> findByTitle(String title) {
        return categoryRepository.findByTitle(title)
                .stream()
                .map(categoryReadMapper::map)
                .collect(Collectors.toList());
    }
}
