package com.codmind.orderapi.validators;

import com.codmind.orderapi.entity.Product;
import com.codmind.orderapi.exceptions.ValidateServiceException;

public class ProductValidator {

    private ProductValidator() {
    }

    public static void save(Product product) {
        if((product.getName() == null) || product.getName().trim().isEmpty()) throw (new ValidateServiceException("El nombre es requerido"));
        if(product.getName().length() > 100) throw (new ValidateServiceException(("El nombre debe tener máximo 100 caracteres")));
        if(product.getPrice() == null) throw (new ValidateServiceException(("El precio es requerido")));
        if(product.getPrice() <= 0) throw (new ValidateServiceException(("El precio debe tener un valor mayor o igual a cero (0)")));
        }
    }