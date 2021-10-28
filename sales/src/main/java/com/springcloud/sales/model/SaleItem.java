package com.springcloud.sales.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "SALE_ITEM")
public class SaleItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_SALE")
    private Sale sale;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ID_PRODUCT")
    private Product product;

    private BigDecimal quantity;

    private BigDecimal value;

    private BigDecimal totalValue;


    public SaleItem(
            @NonNull Product product,
            @NonNull BigDecimal quantity,
            @NonNull BigDecimal value) {
        this.product = Objects.requireNonNull(product, "produto não pode ser nulo");
        this.quantity = Objects.requireNonNull(quantity, "quantidade não pode ser nulo");
        this.value = Objects.requireNonNull(value, "valor não pode ser nulo");
        this.totalValue = quantity.multiply(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleItem saleItem = (SaleItem) o;
        return Objects.equals(sale, saleItem.sale) && Objects.equals(product, saleItem.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sale, product);
    }
}
