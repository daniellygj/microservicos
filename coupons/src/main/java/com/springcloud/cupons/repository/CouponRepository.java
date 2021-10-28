package com.springcloud.cupons.repository;

import com.springcloud.cupons.model.Coupon;
import org.springframework.data.repository.CrudRepository;

public interface CouponRepository extends CrudRepository<Coupon, String> {
}
