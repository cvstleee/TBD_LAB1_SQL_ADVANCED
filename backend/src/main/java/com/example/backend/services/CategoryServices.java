package com.example.backend.services;

import com.example.backend.entities.Category;
import com.example.backend.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryServices {

    private final CategoryRepository categoryRepository;
    public CategoryServices(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoriesById(long id) {
        return categoryRepository.findById(id);
    }

    public Category postCategorie(Category category) {
        return categoryRepository.save(category);
    }
    public Category putCategorie(long id, Category category) {
        return categoryRepository.update(id, category);
    }

    public boolean softDeleteCategory(long id) {
        Category category = getCategoriesById(id);
        if (category == null) {
            throw new NoSuchElementException("Category not found");
        }
        return categoryRepository.softDelete(id);
    }
}
