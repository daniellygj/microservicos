package com.springcloud.sales.controller;

import com.springcloud.sales.converter.SaleDtoConverter;
import com.springcloud.sales.dto.SaleDTO;
import com.springcloud.sales.model.Sale;
import com.springcloud.sales.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class SaleController {

    @Autowired
    private SaleDtoConverter converter;

    @Autowired
    private SaleRepository repository;

    @PostMapping
    public Sale save(@RequestBody SaleDTO saleDTO) {
        Sale sale = converter.toSale(saleDTO);
        return repository.save(sale);
    }

    @GetMapping
    public List<Sale> findAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Optional<Sale> findById(@PathVariable("id") Long id) {
        return repository.findById(id);
    }
}
