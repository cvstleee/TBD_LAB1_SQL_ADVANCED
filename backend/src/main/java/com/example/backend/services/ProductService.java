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
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId);

        if (categoryEntity == null) {
            throw new EntityNotFoundException("Category not found");
        }else {
            product.setCategory_id(categoryEntity.getId());
            return productRepository.save(product);

        }
    }

    public ProductEntity updateProduct(Long id, ProductEntity product) {
        return productRepository.update(id, product);
    }

    public boolean deleteProduct(Long id) {
        ProductEntity product = productRepository.findById(id);
        if (product == null) {
            throw new EntityNotFoundException("Product not found");
        }
        return productRepository.delete(id);
    }

}
