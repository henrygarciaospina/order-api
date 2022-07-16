package com.codmind.orderapi.controller;

import com.codmind.orderapi.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codmind.orderapi.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    private final List<Product> products = new ArrayList<>();

    public ProductController() {

        for(int c = 0; c<= 10; c++ ) {
           products.add(Product.builder()
                   .id((c+1L))
                   .name("Product " + (c+1L))
                   .build()
           ) ;
        }
    }
    @GetMapping(value="/products/{productId}")
    public ResponseEntity<Product> findById(@PathVariable("productId") Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("No existe el producto"));

        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    @DeleteMapping(value="/products/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("No existe el producto"));

        productRepository.delete(product);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value="/products")
    public List<Product> findAll(){
        return this.products;
    }
    @PostMapping(value="/products")
    public Product create(@RequestBody Product product){
        this.products.add(product);
        return product;
    }
    @PutMapping(value="/products")
    public Product update(@RequestBody Product product){
        for(Product prod: this.products) {
            if(prod.getId().longValue() == product.getId().longValue()){
                prod.setName(product.getName());
                return prod;
            }
         }
        throw new RuntimeException("No existe el producto");
    }
}