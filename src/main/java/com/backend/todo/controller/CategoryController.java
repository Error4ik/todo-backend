package com.backend.todo.controller;

import com.backend.todo.dto.CategoryCreateEditDto;
import com.backend.todo.dto.CategoryReadDto;
import com.backend.todo.exception.NotFoundException;
import com.backend.todo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        CategoryReadDto categoryReadDto = categoryService.create(categoryCreateEditDto);
        return new ResponseEntity<>(categoryReadDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryReadDto> update(@PathVariable UUID id, @RequestBody @Validated CategoryCreateEditDto categoryCreateEditDto) {
        CategoryReadDto categoryReadDto = categoryService.update(id, categoryCreateEditDto)
                .orElseThrow(() -> new NotFoundException("There is no object with this ID"));

        return ok(categoryReadDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryReadDto> findById(@PathVariable UUID id) {
        CategoryReadDto categoryReadDto = categoryService.findById(id)
                .orElseThrow(() -> new NotFoundException("There is no object with this ID"));

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
        return ok(categoryService.findByTitle(title));
    }
}
