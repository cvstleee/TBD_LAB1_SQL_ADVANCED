package com.example.backend.services;

import com.example.backend.entities.Product;
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

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(long id) {
        Product product = productRepository.findById(id);

        if (product == null) {
            throw new NoSuchElementException("Product not found");
        }

        return product;
    }

    public Product postProduct(Product product) {
        return productRepository.save(product);
    }

    public Product putProduct(long id, Product product) {
        return productRepository.update(id, product);
    }

    public boolean softDeleteProduct(long id) {
        Product product = getProduct(id);
        if (product == null) {
            throw new NoSuchElementException("Product not found");
        }
        return productRepository.softDelete(id);
    }

}
