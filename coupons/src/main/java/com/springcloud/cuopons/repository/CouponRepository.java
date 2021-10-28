package com.springcloud.cuopons.repository;

import com.springcloud.cuopons.model.Coupon;
import org.springframework.data.repository.CrudRepository;

public interface CouponRepository extends CrudRepository<Coupon, String> {
}
