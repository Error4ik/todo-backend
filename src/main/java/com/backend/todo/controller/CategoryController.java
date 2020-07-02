package com.backend.todo.controller;

import com.backend.todo.domain.Category;
import com.backend.todo.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping("/categories")
    public List<Category> getCategories() {
        return this.categoryService.getCategories();
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
}
