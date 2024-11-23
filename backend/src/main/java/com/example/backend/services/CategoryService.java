package com.example.backend.services;

import com.example.backend.entities.CategoryEntity;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuthService authService;

    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    public CategoryEntity getCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id);
        if (category == null) {
            throw new EntityNotFoundException("Category not found");
        }

        return categoryRepository.findById(id);
    }

    public CategoryEntity createCategory(CategoryEntity category) {
        CategoryEntity existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory != null) {
            throw new IllegalStateException("Category name already exists");
        }
        int authIdClient = authService.getAuthIdClient();

        return categoryRepository.save(category, authIdClient);
    }

    public CategoryEntity updateCategory(Long id, CategoryEntity category) {
        int authIdClient = authService.getAuthIdClient();
        CategoryEntity possibleCategory = categoryRepository.findById(id);
        if (possibleCategory == null) {
            throw new EntityNotFoundException("Category not found");
        }
        CategoryEntity existingCategory = categoryRepository.findByNameAndNotId(category.getName(), id);
        if (existingCategory != null) {
            throw new IllegalStateException("Category name already exists");
        }

        return categoryRepository.update(id, category, authIdClient);
    }

    public boolean deleteCategory(Long id) {
        int authIdClient = authService.getAuthIdClient();
        CategoryEntity possibleCategory = categoryRepository.findById(id);
        if (possibleCategory == null) {
            throw new EntityNotFoundException("Category not found");
        }

        return categoryRepository.delete(id, authIdClient);
    }
}
