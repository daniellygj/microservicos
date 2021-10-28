package com.springcloud.sales.converter;

import com.springcloud.sales.dto.SaleDTO;
import com.springcloud.sales.dto.SaleItemDTO;
import com.springcloud.sales.exception.ProductNotFoundException;
import com.springcloud.sales.model.Product;
import com.springcloud.sales.model.Sale;
import com.springcloud.sales.model.SaleItem;
import com.springcloud.sales.repository.ProductRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SaleDtoConverter {

    private final ProductRepository productRepository;

    public SaleDtoConverter(ProductRepository repository) {
        this.productRepository = repository;
    }

    public Sale toSale(SaleDTO saleDTO) {
        Set<SaleItem> saleItemList = new HashSet<>(saleDTO.getItens().size());
        for (SaleItemDTO itemDTO : saleDTO.getItens()) {
            Optional<Product> optionalProduct = productRepository.findById(itemDTO.getProduct());
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                SaleItem saleItem = new SaleItem(product, itemDTO.getQuantity(), itemDTO.getValue());
                saleItemList.add(saleItem);
            }
        }
//        todo - comunicacao com o servico de cupons para aplicar o desconto
        return new Sale(saleItemList, BigDecimal.ZERO, saleDTO.getEmail());
    }
}
