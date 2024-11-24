package com.example.backend.controllers;

import com.example.backend.entities.ProductEntity;
import com.example.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts() {
        List<ProductEntity> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @PostMapping("/{categoryId}")
    public ResponseEntity<?> postProduct(@RequestBody ProductEntity product, @PathVariable Long categoryId) {
        return new ResponseEntity<>(productService.createProduct(product, categoryId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putProduct(@PathVariable int id, @RequestBody ProductEntity product) {
        try {
            return new ResponseEntity<>(productService.updateProduct(id, product), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        HashMap<String, Boolean> response = new HashMap<>();
        response.put("success", productService.deleteProduct(id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/category/{categoryId}/{rate}")
    public ResponseEntity<List<ProductEntity>> putProductsPriceWithRateByCategory(
            @PathVariable int categoryId, @PathVariable double rate
    ) {
        return new ResponseEntity<>(productService.updateProductsPriceWithRateByCategory(rate, categoryId), HttpStatus.OK);
    }
}
