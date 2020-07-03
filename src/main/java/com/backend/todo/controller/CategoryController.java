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
        logger.info(String.format("Input arguments: %s", category));
        if (category.getId() != null) {
            logger.info("The redundant parameter: id must be null");
            return new ResponseEntity("The redundant parameter: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().isEmpty()) {
            logger.info("Missed parameters: title");
            return new ResponseEntity("Missed parameter: title", HttpStatus.NOT_ACCEPTABLE);
        }
        Category cat = categoryService.addCategory(category);
        logger.info(String.format("Save: %s", cat));
        return ResponseEntity.ok(cat);
    }

    @PutMapping("/update")
    public ResponseEntity<Category> updateCategory(@NonNull Category category) {
        logger.info(String.format("Input arguments: %s", category));
        if (category.getId() == null) {
            logger.info("Missed parameter: id must not be null");
            return new ResponseEntity("Missed parameter: id must not be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (category.getTitle() == null || category.getTitle().trim().isEmpty()) {
            logger.info("Missed parameters: title");
            return new ResponseEntity("Missed parameter: title", HttpStatus.NOT_ACCEPTABLE);
        }
        Category cat = this.categoryService.updateCategory(category);
        logger.info(String.format("Update: %s", cat));
        return ResponseEntity.ok(cat);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        Optional<Category> category = this.categoryService.getCategoryById(id);
        if (!category.isPresent()) {
            logger.info("There is no entity with this ID!");
            return new ResponseEntity("There is no entity with this ID!", HttpStatus.NOT_FOUND);
        }
        logger.info(String.format("Return: %s", category.get()));
        return ResponseEntity.ok(category.get());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable UUID id) {
        logger.info(String.format("Input arguments: %s", id));
        try {
            this.categoryService.deleteCategory(id);
        } catch (EmptyResultDataAccessException e) {
            this.logger.error("There is no entity with this ID!");
        }
        logger.info(String.format("Category was deleted: %s", id));
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/find-by-title")
    public ResponseEntity<List<Category>> findByTitle(@RequestParam String title) {
        logger.info(String.format("Input arguments: %s", title));
        return ResponseEntity.ok(this.categoryService.findByTitle(title));
    }
}
