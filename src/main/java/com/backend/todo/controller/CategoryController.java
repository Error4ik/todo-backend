package com.backend.todo.controller;

import com.backend.todo.domain.Category;
import com.backend.todo.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/categories")
    public List<Category> getCategories() {
        return this.categoryService.findAllByOrderByTitleAsc();
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@NonNull Category category) {
        if (category.getId() != null) {
            return new ResponseEntity("The redundant param: id parameter must be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().isEmpty()) {
            return new ResponseEntity("Missed parameters: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@NonNull Category category) {
        if (category.getId() == null) {
            return new ResponseEntity("Missed: the id parameter must not be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().isEmpty()) {
            return new ResponseEntity("Missed parameters: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(this.categoryService.updateCategory(category));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        Optional<Category> category = this.categoryService.getCategoryById(id);
        if (!category.isPresent()) {
            return new ResponseEntity("There is no entity with this ID!", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(category.get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable UUID id) {
        try {
            this.categoryService.deleteCategory(id);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("There is no entity with this ID!");
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
