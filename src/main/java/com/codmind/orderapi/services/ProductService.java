package com.codmind.orderapi.services;

import com.codmind.orderapi.entity.Product;
import com.codmind.orderapi.exceptions.NoDataFoundException;
import com.codmind.orderapi.repository.ProductRepository;
import com.codmind.orderapi.validators.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
                .orElseThrow(() -> new NoDataFoundException(MESSAGE));
    }

    @Transactional
    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoDataFoundException(MESSAGE));
        productRepository.delete(product);
    }
    public List<Product> findAll(Pageable page) {
        return productRepository.findAll(page).toList();
    }

    //Crea registro
    @Transactional
    public Product save(Product product){

        ProductValidator.save(product);
        if(product.getId() == null ){
            return productRepository.save(product);
        }
        //Actualiza registro
        Product existProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new NoDataFoundException(MESSAGE));

        existProduct.setName(product.getName());
        existProduct.setPrice(product.getPrice());

        return productRepository.save(existProduct);
    }
}