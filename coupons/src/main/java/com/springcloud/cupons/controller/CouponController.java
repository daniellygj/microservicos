package com.springcloud.cupons.controller;

import com.springcloud.cupons.exception.CouponNotFoundException;
import com.springcloud.cupons.model.Coupon;
import com.springcloud.cupons.repository.CouponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping
public class CouponController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CouponController.class);

    private final CouponRepository repository;

    public CouponController(CouponRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<Coupon> findAll() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public Coupon findById(@NonNull @PathVariable("id") String id) {
        LOGGER.info("Seeking the coupon: id = {}", id);

        return repository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));
    }

    @PostMapping
    public ResponseEntity<Coupon> newCoupon(
            @NonNull @RequestParam("discount") BigDecimal desconto) {
        LOGGER.info("Inserting a new coupon: discount = {}", desconto);

        Coupon coupon = new Coupon(desconto);
        Coupon savedCoupon = repository.save(coupon);

        return ResponseEntity.ok(savedCoupon);
    }

}
