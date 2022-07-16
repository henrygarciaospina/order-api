package com.codmind.orderapi.controller;

import com.codmind.orderapi.entity.Product;
import com.codmind.orderapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping(value = "/products/{productId}")
    public ResponseEntity<Product> findById(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @DeleteMapping(value = "/products/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/products")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @PostMapping(value = "/products")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product newProduct = productService.save(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }
    @PutMapping(value = "/products")
    public ResponseEntity<Product> update(@RequestBody Product product) {
        Product updateProduct = productService.save(product);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }
}