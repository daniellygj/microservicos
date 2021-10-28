package com.springcloud.coupons.repository;

import com.springcloud.coupons.model.Coupon;
import org.springframework.data.repository.CrudRepository;

public interface CouponRepository extends CrudRepository<Coupon, String> {
}
