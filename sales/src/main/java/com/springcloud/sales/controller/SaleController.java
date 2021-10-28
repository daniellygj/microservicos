package com.springcloud.sales.controller;

import com.springcloud.sales.converter.SaleDtoConverter;
import com.springcloud.sales.dto.SaleDTO;
import com.springcloud.sales.exception.SaleNotFoundException;
import com.springcloud.sales.model.Sale;
import com.springcloud.sales.repository.SaleRepository;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("")
public class SaleController {

    @Autowired
    private SaleDtoConverter converter;

    @Autowired
    private SaleRepository repository;

    @Autowired
    private SaleModelAssembler saleModelAssembler;

    @PostMapping
    public EntityModel<Sale> save(@RequestBody SaleDTO saleDTO) {
        Sale sale = converter.toSale(saleDTO);
        return saleModelAssembler.toModel(repository.save(sale));
    }

    @GetMapping
    public CollectionModel<EntityModel<Sale>> findAll(
            @NonNull @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @NonNull @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {

        PageRequest pageable = PageRequest.of(page, size);

        List<EntityModel<Sale>> sales = repository.findAll().stream()
                .map(saleModelAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(sales, linkTo(methodOn(SaleController.class).findAll(size, page))
                .withSelfRel());
    }

    @GetMapping("{id}")
    public EntityModel<Sale> findById(@PathVariable("id") Long id) {
        Sale sale = repository.findById(id).orElseThrow(() -> new SaleNotFoundException(id));
        return saleModelAssembler.toModel(sale);
    }
}
