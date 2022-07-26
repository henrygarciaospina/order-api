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

    @Autowired
    private ProductConverter productConverter;
    @GetMapping(value = "/products/{productId}")
    public  ResponseEntity<WrapperResponse<ProductDTO>> findById(@PathVariable("productId") Long productId) {
        Product product = productService.findById(productId);
        ProductDTO productDTO = productConverter.fromEntity(product);
        return new WrapperResponse<ProductDTO>(true, "success", productDTO)
                .createResponse(HttpStatus.OK);
    }
    @DeleteMapping(value = "/products/{productId}")
    public ResponseEntity<?> delete(@PathVariable("productId") Long productId) {
        productService.delete(productId);
        return new WrapperResponse(true, "success", null)
                .createResponse(HttpStatus.OK);
    }
    @GetMapping(value = "/products")
    public ResponseEntity<List<ProductDTO>> findAll(
            @RequestParam(value="pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value="pageSize", required = false, defaultValue = "5") int pageSize) {

        Pageable page = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productService.findAll(page);
        List<ProductDTO> dtoProducts = productConverter.fromEntity(products);

        return new WrapperResponse(true, "success", dtoProducts)
                .createResponse(HttpStatus.OK);
    }
    @PostMapping(value = "/products")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO product) {
        Product newProduct = productService.save(productConverter.fromDTO(product));
        ProductDTO productDTO = productConverter.fromEntity(newProduct);

        return new WrapperResponse(true, "success", productDTO)
                .createResponse(HttpStatus.CREATED);
    }
    @PutMapping(value = "/products")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO product) {
        Product updateProduct = productService.save(productConverter.fromDTO(product));
        ProductDTO productDTO = productConverter.fromEntity(updateProduct);

        return new WrapperResponse(true, "success", productDTO)
                .createResponse(HttpStatus.OK);
    }
}