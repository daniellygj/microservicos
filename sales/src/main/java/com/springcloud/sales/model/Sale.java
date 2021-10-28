package com.springcloud.sales.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "SALE")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private LocalDateTime date;

    private BigDecimal value;

    private BigDecimal discount;

    @JsonManagedReference
    @OneToMany(mappedBy = "sale", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<SaleItem> itens = new HashSet<>();

    @Deprecated
    public Sale() {
    }

    public Sale(@NonNull Set<SaleItem> itens, @NonNull BigDecimal discount, @NonNull String email) {
        this.modify(itens, discount, email);
        this.date = LocalDateTime.now();
    }

    private void aplicarDesconto() {
        if (discount.compareTo(BigDecimal.ZERO) > 0) {
            var valorDesconto = value.multiply(discount)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);
            value = value.subtract(valorDesconto);
        }
    }

    public void modify(@NonNull Set<SaleItem> itens, @NonNull BigDecimal desconto, @NonNull String email) {
        this.discount = Objects.requireNonNull(desconto, "discount cannot be null");
        this.email = Objects.requireNonNull(email, "email cannot be null");
        Objects.requireNonNull(itens, "itens list cannot be null");

        itens.forEach(it -> it.setSale(this));

        if (!this.itens.isEmpty()) {
            this.itens.clear();
        }
        this.itens.addAll(itens);


        this.value = itens.stream()
                .map(SaleItem::getTotalValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.aplicarDesconto();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
