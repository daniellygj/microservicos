package com.springcloud.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springcloud.product.model.Product;
import com.springcloud.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public List<Product> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> findById(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PostMapping
    public Product insert(@RequestBody Product product) throws JsonProcessingException {
        Product productSaved = repository.save(product);

        var productJson = mapper.writeValueAsString(productSaved);
        jmsTemplate.convertAndSend("queue.product.insert", productJson);

        return productSaved;
    }

    @DeleteMapping
    public void delete(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }
}
