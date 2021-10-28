package com.springcloud.sales.exception;

import javax.persistence.NoResultException;

public class SaleNotFoundException extends NoResultException {

    public SaleNotFoundException(Long id) {
        super("Sale not found: ID[" + id + "]");
    }

}
