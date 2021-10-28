package com.springcloud.sales.converter;

import com.springcloud.sales.dto.SaleDTO;
import com.springcloud.sales.exception.ProductNotFoundException;
import com.springcloud.sales.model.Sale;
import com.springcloud.sales.model.SaleItem;
import com.springcloud.sales.repository.ProductRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class SaleDtoConverter {

    private final ProductRepository repository;

    public SaleDtoConverter(ProductRepository repository) {
        this.repository = repository;
    }

//    public SaleDtoConverter(ProductRepository productRepository, CupomExternalApi cupomExternalApi) {
//        this.repository = productRepository;
//        this.cupomExternalApi = cupomExternalApi;
//    }

    public Sale toSale(SaleDTO saleDTO) {
        var itens = toSaleItem(saleDTO);
        var discount = getDiscount(saleDTO);
        return new Sale(itens, discount, saleDTO.getEmail());
    }

    private BigDecimal getDiscount(SaleDTO saleDTO) {
//        todo - comunicacao com o servico de cupons para aplicar o desconto
        return BigDecimal.ZERO;
    }

    private Set<SaleItem> toSaleItem(SaleDTO saleDTO) {
        return saleDTO.getItens().stream()
                .map(it -> {
                    var produto = repository.findById(it.getProduct())
                            .orElseThrow(() -> new ProductNotFoundException(it.getProduct()));
                    return new SaleItem(produto, it.getQuantity(), it.getValue());
                })
                .collect(Collectors.toSet());
    }

}
