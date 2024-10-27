package com.example.backend.services;

import com.example.backend.entities.CategoryEntity;
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
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryEntity getCategoriesById(long id) {
        return categoryRepository.findById(id);
    }

    public CategoryEntity postCategorie(CategoryEntity category) {
        return categoryRepository.save(category);
    }
    public CategoryEntity putCategorie(long id, CategoryEntity category) {
        return categoryRepository.update(id, category);
    }

    public boolean softDeleteCategory(long id) {
        CategoryEntity category = getCategoriesById(id);
        if (category == null) {
            throw new NoSuchElementException("Category not found");
        }
        return categoryRepository.softDelete(id);
    }
}
