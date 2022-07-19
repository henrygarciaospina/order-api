package com.codmind.orderapi.services;

import com.codmind.orderapi.entity.Product;
import com.codmind.orderapi.exceptions.GeneralServiceException;
import com.codmind.orderapi.exceptions.NoDataFoundException;
import com.codmind.orderapi.exceptions.ValidateServiceException;
import com.codmind.orderapi.repository.ProductRepository;
import com.codmind.orderapi.validators.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    static final String MESSAGE = "No existe el producto";

    public Product findById(Long productId) {
        try{
            return productRepository.findById(productId)
                    .orElseThrow(() -> new NoDataFoundException(MESSAGE));
        } catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
    @Transactional
    public void delete(Long productId) {
       try{
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoDataFoundException(MESSAGE));
            productRepository.delete(product);
        } catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
    public List<Product> findAll(Pageable page) {
        try{
            return productRepository.findAll(page).toList();
        } catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    //Crea registro
    @Transactional
    public Product save(Product product){
        try{
            ProductValidator.save(product);
            if(product.getId() == null ){
                return productRepository.save(product);
            }
        } catch (ValidateServiceException | NoDataFoundException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
        //Actualiza registro
        Product existProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new NoDataFoundException(MESSAGE));

        existProduct.setName(product.getName());
        existProduct.setPrice(product.getPrice());

        return productRepository.save(existProduct);
    }
}