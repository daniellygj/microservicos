package com.springcloud.coupons.exception;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException(String id) {
        super("Coupon not found: ID[" + id + "]");
    }

}
