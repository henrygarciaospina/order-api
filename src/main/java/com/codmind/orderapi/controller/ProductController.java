package com.codmind.orderapi.controller;

import com.codmind.orderapi.converters.ProductConverter;
import com.codmind.orderapi.dtos.ProductDTO;
import com.codmind.orderapi.entity.Product;
import com.codmind.orderapi.services.ProductService;
import com.codmind.orderapi.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    private final ProductConverter productConverter = new ProductConverter();
    @GetMapping(value = "/products/{productId}")
    public  ResponseEntity<WrapperResponse<ProductDTO>> findById(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);
        ProductDTO productDTO = productConverter.fromEntity(product);
        WrapperResponse<ProductDTO> response = new WrapperResponse<>(true, "success", productDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping(value = "/products/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductDTO>> findAll(
            @RequestParam(value="pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value="pageSize", required = false, defaultValue = "5") int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productService.findAll(page);
        List<ProductDTO> dtoProducts = productConverter.fromEntity(products);
        return new ResponseEntity<>(dtoProducts, HttpStatus.OK);
    }
    @PostMapping(value = "/products")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
        Product newProduct = productService.save(productConverter.fromDTO(productDTO));
        ProductDTO productDto = productConverter.fromEntity(newProduct);
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }
    @PutMapping(value = "/products")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO) {
        Product updateProduct = productService.save(productConverter.fromDTO(productDTO));
        ProductDTO productDto = productConverter.fromEntity(updateProduct);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
}