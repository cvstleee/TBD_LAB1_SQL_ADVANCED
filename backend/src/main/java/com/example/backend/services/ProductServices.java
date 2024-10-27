package com.example.backend.services;

import com.example.backend.entities.ProductEntity;
import com.example.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServices {
    private final ProductRepository productRepository;

    public ProductServices(ProductRepository productRepository) {
        this.productRepository = productRepository;
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

    public ProductEntity postProduct(ProductEntity product) {
        return productRepository.save(product);
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
