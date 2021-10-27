package com.springcloud.product.controller;

import com.springcloud.product.model.Product;
import com.springcloud.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping
    public List<Product> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> findById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public Product insert(@RequestBody Product product) {
        return repository.save(product);
    }

    @DeleteMapping
    public void delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}
