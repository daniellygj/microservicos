package com.springcloud.sales.controller;

import com.springcloud.sales.model.Sale;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SaleModelAssembler implements RepresentationModelAssembler<Sale, EntityModel<Sale>> {

    @Override
    public EntityModel<Sale> toModel(Sale sale) {
        return EntityModel.of(sale,
                linkTo(methodOn(SaleController.class).findById(sale.getId())).withRel("byId"));
//                linkTo(methodOn(SaleController.class).findAll(null, null)).withRel("sales"));
    }

}
