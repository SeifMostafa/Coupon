package com.musala.couponservice.controllers;

import com.musala.couponservice.model.Coupon;
import com.musala.couponservice.repos.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CouponController {

    @Autowired
    CouponRepo couponRepo;

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }

    @GetMapping("/showCreateCoupon")
    public String showCreateCoupon() {
        return "createCoupon";
    }

    @PostMapping("/saveCoupon")
    public String saveCoupon(Coupon coupon) {
        couponRepo.save(coupon);
        return "createResponse";
    }

    @GetMapping("/showGetCoupon")
    public String showGetCoupon() {
        return "getCoupon";
    }

    @PostMapping("/getCoupon")
    public ModelAndView getCoupon(String code) {
        ModelAndView couponDetails = new ModelAndView("couponDetails");
        couponDetails.addObject(couponRepo.findByCode(code));
        return couponDetails;
    }
}
