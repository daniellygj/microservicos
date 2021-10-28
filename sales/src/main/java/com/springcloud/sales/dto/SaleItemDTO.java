package com.springcloud.sales.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SaleItemDTO {

    @NotNull
    private Long product;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal value;

    public SaleItemDTO() {
    }


}
