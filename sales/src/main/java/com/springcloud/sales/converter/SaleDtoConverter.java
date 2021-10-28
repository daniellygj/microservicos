package com.springcloud.sales.converter;

import com.springcloud.sales.dto.SaleDTO;
import com.springcloud.sales.dto.SaleItemDTO;
import com.springcloud.sales.external.CouponExternalApi;
import com.springcloud.sales.model.Product;
import com.springcloud.sales.model.Sale;
import com.springcloud.sales.model.SaleItem;
import com.springcloud.sales.repository.ProductRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class SaleDtoConverter {

    private final ProductRepository productRepository;

    private final CouponExternalApi couponExternalApi;

    public SaleDtoConverter(ProductRepository repository, CouponExternalApi couponExternalApi) {
        this.productRepository = repository;
        this.couponExternalApi = couponExternalApi;
    }

    public Sale toSale(SaleDTO saleDTO) {
        Set<SaleItem> saleItems =  ToSaleItem(saleDTO);
        BigDecimal discount = getDesconto(saleDTO);

//        todo - comunicacao com o servico de cuopons para aplicar o desconto
        return new Sale(saleItems, discount, saleDTO.getEmail());
    }

    public Set<SaleItem> ToSaleItem(SaleDTO saleDTO) {
        Set<SaleItem> saleItemList = new HashSet<>(saleDTO.getItens().size());

        for (SaleItemDTO itemDTO : saleDTO.getItens()) {
            Optional<Product> optionalProduct = productRepository.findById(itemDTO.getProduct());
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                SaleItem saleItem = new SaleItem(product, itemDTO.getQuantity(), itemDTO.getValue());
                saleItemList.add(saleItem);
            }
        }

        return saleItemList;
    }

    private BigDecimal getDesconto(SaleDTO saleDTO) {
        if (saleDTO.getCoupon().isPresent() && StringUtils.isNotBlank(saleDTO.getCoupon().get())) {
            return couponExternalApi.getDiscountByCoupon(saleDTO.getCoupon().get());
        }
        return BigDecimal.ZERO;
    }
}
