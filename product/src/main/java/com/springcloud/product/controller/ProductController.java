package com.springcloud.product.controller;

import com.springcloud.product.model.Product;
import com.springcloud.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public List<Product> findAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Product> findById(@PathVariable Long id) {
        return repository.findById(id);
    }


}
