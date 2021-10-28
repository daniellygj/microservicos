package com.springcloud.sales.external;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Objects;

@Component
public class CouponExternalApi {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "noDiscount")
    public BigDecimal getDiscountByCoupon(@NotNull String coupon) {
        Objects.requireNonNull(coupon, "Coupon code cannot be null");
        String url = "http://coupons/" + coupon;
        return restTemplate.getForObject(url, BigDecimal.class);
    }

    public BigDecimal noDiscount(String coupon) {
        return BigDecimal.ZERO;
    }

}
