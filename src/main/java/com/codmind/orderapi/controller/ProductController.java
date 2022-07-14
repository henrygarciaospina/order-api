package com.codmind.orderapi.controller;

import com.codmind.orderapi.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import repository.ProductRepository;

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
    public Product findById(@PathVariable("productId") Long productId){
        for(Product prod: this.products) {
            if(prod.getId().longValue() == productId.longValue()){
                return prod;
            }
        }
        return null;
    }
    @DeleteMapping(value="/products/{productId}")
    public void delete(@PathVariable("productId") Long productId){
        Product deleteProduct = null;
        for(Product prod: this.products) {
            if(prod.getId().longValue() == productId.longValue()){
                deleteProduct = prod;
                break;
            }
        }

        if(deleteProduct == null) throw new RuntimeException("No existe el producto");

        this.products.remove(deleteProduct);
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