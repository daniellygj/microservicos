package com.springcloud.coupons.config;

import com.springcloud.coupons.exception.CouponNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdviceConfiguration {

    @ResponseBody
    @ExceptionHandler(CouponNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noResultExceptionHandler(CouponNotFoundException e) {
        return e.getMessage();
    }

}
