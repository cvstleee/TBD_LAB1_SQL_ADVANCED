package com.example.backend.services;

import com.example.backend.entities.CategoryEntity;
import com.example.backend.entities.ProductEntity;
import com.example.backend.repositories.CategoryRepository;
import com.example.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductServices {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServices(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity getProduct(long id) {
        ProductEntity product = productRepository.findById(id);

        if (product == null) {
            throw new NoSuchElementException("Product not found");
        }

        return product;
    }

    public ProductEntity postProduct(ProductEntity product, long categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId);

        if (categoryEntity == null) {
            throw new NoSuchElementException("Category not found");
        }else {
            product.setCategory_id(categoryEntity.getId());
            return productRepository.save(product);

        }
    }

    public ProductEntity putProduct(long id, ProductEntity product) {
        return productRepository.update(id, product);
    }

    public boolean softDeleteProduct(long id) {
        ProductEntity product = getProduct(id);
        if (product == null) {
            throw new NoSuchElementException("Product not found");
        }
        return productRepository.softDelete(id);
    }

}
