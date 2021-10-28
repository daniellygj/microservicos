package com.springcloud.cupons.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Objects;

@RedisHash(value = "cupom", timeToLive = 60 * 60)
public class Coupon {

    @Id
    private String id;

    private BigDecimal discount;


    public Coupon(@NonNull BigDecimal discount) {
        this.discount = Objects.requireNonNull(discount, "discount cannot be null");
        this.id = RandomStringUtils.randomAlphanumeric(5);
    }

    public String getId() {
        return id;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", discount=" + discount +
                '}';
    }
}
