package com.springcloud.sales.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@NoArgsConstructor
public class SaleDTO {

    private final JsonNullable<String> coupon = JsonNullable.undefined();

    @NotNull
    private Set<SaleItemDTO> itens;

    @NotNull
    private String email;
}
