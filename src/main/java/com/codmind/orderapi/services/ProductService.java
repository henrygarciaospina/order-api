package com.codmind.orderapi.services;

import com.codmind.orderapi.entity.Product;
import com.codmind.orderapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    static final String MESSAGE = "No existe el producto";

    public Product findById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(MESSAGE));
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(MESSAGE));
        productRepository.delete(product);
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    //Crea registro
    @Transactional
    public Product save(Product product){
        if(product.getId() == null ){
            return productRepository.save(product);
        }
        //Actualiza registro
        Product existProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException(MESSAGE));

        existProduct.setName(product.getName());
        existProduct.setPrice(product.getPrice());

        return productRepository.save(existProduct);
    }
}