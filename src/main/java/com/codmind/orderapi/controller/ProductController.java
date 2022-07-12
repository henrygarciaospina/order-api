package com.codmind.orderapi.controller;

import com.codmind.orderapi.entity.Product;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

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

    @GetMapping(value="/products")
    public List<Product> findAll(){
        return this.products;
    }

    @GetMapping(value="/products/{productId}")
    public Product findById(@PathVariable("productId") Long productId){
        for(Product prod: this.products) {
            if(prod.getId().longValue() == productId.longValue()){
                return prod;
            }
        }
        return null;
    }

    @PostMapping(value="/products")
    public Product create(@RequestBody Product product){
        this.products.add(product);
        return product;
    }
}