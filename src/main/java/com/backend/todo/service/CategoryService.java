package com.backend.todo.service;

import com.backend.todo.domain.Category;
import com.backend.todo.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public List<Category> findAllByOrderByTitleAsc() {
        return this.categoryRepository.findAllByOrderByTitleAsc();
    }

    public Category addCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        return this.categoryRepository.save(category);
    }

    public Optional<Category> getCategoryById(UUID id) {
        return this.categoryRepository.findById(id);
    }

    public void deleteCategory(UUID id) {
        this.categoryRepository.deleteById(id);
    }
}
