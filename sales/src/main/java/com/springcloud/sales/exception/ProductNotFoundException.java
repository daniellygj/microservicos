package com.springcloud.sales.exception;

import javax.persistence.NoResultException;

public class ProductNotFoundException extends NoResultException {

    public ProductNotFoundException(Long id) {
        super("Product not found: ID[" + id + "]");
    }

}
