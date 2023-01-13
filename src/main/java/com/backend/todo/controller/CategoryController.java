package com.backend.todo.controller;

import com.backend.todo.dto.CategoryCreateEditDto;
import com.backend.todo.dto.CategoryReadDto;
import com.backend.todo.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.*;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryReadDto> findAllByOrderByTitleAsc() {
        return categoryService.findAllByOrderByTitleAsc();
    }

    @PostMapping
    public ResponseEntity<CategoryReadDto> create(@RequestBody @Validated CategoryCreateEditDto categoryCreateEditDto) {
        logger.info(String.format("Input arguments: %s", categoryCreateEditDto));
        CategoryReadDto categoryReadDto = categoryService.create(categoryCreateEditDto);
        logger.info(String.format("Save: %s", categoryReadDto));
        return new ResponseEntity<>(categoryReadDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryReadDto> update(@PathVariable UUID id, @RequestBody @Validated CategoryCreateEditDto categoryCreateEditDto) {
        logger.info(String.format("Input arguments: %s", categoryCreateEditDto));
        CategoryReadDto categoryReadDto = categoryService.update(id, categoryCreateEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        logger.info(String.format("Update: %s", categoryReadDto));
        return ok(categoryReadDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryReadDto> findById(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        CategoryReadDto categoryReadDto = categoryService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        logger.info(String.format("Return: %s", categoryReadDto));
        return ok(categoryReadDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return categoryService.delete(id)
                ? noContent().build()
                : notFound().build();
    }

    @GetMapping("/find-by-title")
    public ResponseEntity<List<CategoryReadDto>> findByTitle(@RequestParam String title) {
        logger.info(String.format("Input arguments: %s", title));
        return ok(categoryService.findByTitle(title));
    }
}
