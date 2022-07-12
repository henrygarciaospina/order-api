package com.codmind.orderapi.controller;

import com.codmind.orderapi.entity.Product;
import org.springframework.web.bind.annotation.RestController;

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

    public List<Product> findAll(){
        return this.products;
    }


}