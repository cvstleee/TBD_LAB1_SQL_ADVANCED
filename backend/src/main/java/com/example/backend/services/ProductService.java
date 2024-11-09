package com.example.backend.services;

import com.example.backend.entities.CategoryEntity;
import com.example.backend.entities.ProductEntity;
import com.example.backend.exceptions.EntityNotFoundException;
import com.example.backend.repositories.CategoryRepository;
import com.example.backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity getProductById(Long id) {
        ProductEntity product = productRepository.findById(id);
        if (product == null) {
            throw new EntityNotFoundException("Product not found");
        }

        return product;
    }

    public ProductEntity createProduct(ProductEntity product, Long categoryId) {
        CategoryEntity possibleCategory = categoryRepository.findById(categoryId);
        if (possibleCategory == null) {
            throw new EntityNotFoundException("Category not found");
        }

        product.setCategory_id(possibleCategory.getId());
        return productRepository.save(product);
    }

    public ProductEntity updateProduct(Long id, ProductEntity product) {
        ProductEntity possibleProduct = productRepository.findById(id);
        if (possibleProduct == null) {
            throw new EntityNotFoundException("Product not found");
        }

        return productRepository.update(id, product);
    }

    public boolean deleteProduct(Long id) {
        ProductEntity possibleProduct = productRepository.findById(id);
        if (possibleProduct == null) {
            throw new EntityNotFoundException("Product not found");
        }

        return productRepository.delete(id);
    }

}
