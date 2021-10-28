package com.springcloud.cupons.controller;

import com.springcloud.cupons.exception.CouponNotFoundException;
import com.springcloud.cupons.model.Coupon;
import com.springcloud.cupons.repository.CouponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("")
public class CouponController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponController.class);

    @Autowired
    private CouponRepository repository;

    @GetMapping
    public Iterable<Coupon> findAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public BigDecimal findById(@NonNull @PathVariable("id") String id) {
        LOGGER.info("Seeking the coupon: id = {}", id);

        return repository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id)).getDiscount();
    }

    @PostMapping
    public ResponseEntity<Coupon> save(@RequestParam("discount") BigDecimal discount) {
        LOGGER.info("Inserting a new coupon: discount = {}", discount);

        Coupon coupon = new Coupon(discount);
        Coupon savedCoupon = repository.save(coupon);

        return ResponseEntity.ok(savedCoupon);
    }

}
