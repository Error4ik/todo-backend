package com.backend.todo.controller;

import com.backend.todo.dto.CategoryCreateEditDto;
import com.backend.todo.dto.CategoryReadDto;
import com.backend.todo.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/category")
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/categories")
    public List<CategoryReadDto> findAllByOrderByTitleAsc() {
        return categoryService.findAllByOrderByTitleAsc();
    }

    @RequestMapping("/add")
    public ResponseEntity<CategoryReadDto> create(@RequestBody CategoryCreateEditDto categoryCreateEditDto) {
        logger.info(String.format("Input arguments: %s", categoryCreateEditDto));

        if (categoryCreateEditDto.getTitle() == null || categoryCreateEditDto.getTitle().trim().isEmpty()) {
            logger.info("Missed parameters: title");
            return new ResponseEntity("Missed parameter: title", HttpStatus.NOT_ACCEPTABLE);
        }
        CategoryReadDto categoryReadDto = categoryService.create(categoryCreateEditDto);
        logger.info(String.format("Save: %s", categoryReadDto));
        return ok(categoryReadDto);
    }

    @RequestMapping("/update/{id}")
    public ResponseEntity<CategoryReadDto> update(@PathVariable UUID id, @RequestBody CategoryCreateEditDto categoryCreateEditDto) {
        logger.info(String.format("Input arguments: %s", categoryCreateEditDto));

        if (categoryCreateEditDto.getTitle() == null || categoryCreateEditDto.getTitle().trim().isEmpty()) {
            logger.info("Missed parameters: title");
            return new ResponseEntity("Missed parameter: title", HttpStatus.NOT_ACCEPTABLE);
        }
        CategoryReadDto categoryReadDto = categoryService.updateCategory(id, categoryCreateEditDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        ;
        logger.info(String.format("Update: %s", categoryReadDto));
        return ok(categoryReadDto);
    }

    @RequestMapping("/id/{id}")
    public ResponseEntity<CategoryReadDto> getById(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        CategoryReadDto categoryReadDto = categoryService.getCategoryById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        logger.info(String.format("Return: %s", categoryReadDto));
        return ok(categoryReadDto);
    }

    @RequestMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
        return categoryService.delete(id)
                ? noContent().build()
                : notFound().build();
    }

    @RequestMapping("/find-by-title")
    public ResponseEntity<List<CategoryReadDto>> findByTitle(@RequestParam String title) {
        logger.info(String.format("Input arguments: %s", title));
        return ok(categoryService.findByTitle(title));
    }
}
