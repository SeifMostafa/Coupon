package com.musala.couponservice.controllers;

import com.musala.couponservice.model.Coupon;
import com.musala.couponservice.repos.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couponapi")
@CrossOrigin
public class CouponRestController {

    @Autowired
    CouponRepo repo;

    @PostMapping("/coupons")
    @Secured("ADMIN")
    public Coupon create(@RequestBody Coupon coupon) {
        return repo.save(coupon);
    }

    @GetMapping("/coupons/{code}")
    @PostAuthorize("returnObject.discount>50")
    public Coupon getCoupon(@PathVariable String code) {
        return repo.findByCode(code);
    }
}
