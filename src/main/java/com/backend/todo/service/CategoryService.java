package com.backend.todo.service;

import com.backend.todo.domain.Category;
import com.backend.todo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alexey Voronin.
 * @since 30.06.2020.
 */
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    public Category addCategory(Category category) {
        return this.categoryRepository.save(category);
    }
}
