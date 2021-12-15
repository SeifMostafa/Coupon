package com.musala.couponservice.controllers;

import com.musala.couponservice.model.Coupon;
import com.musala.couponservice.model.Role;
import com.musala.couponservice.model.User;
import com.musala.couponservice.repos.RoleRepo;
import com.musala.couponservice.repos.UserRepo;
import com.musala.couponservice.security.SecurityService;
import com.musala.couponservice.security.SecurityServiceImpl;
import com.musala.couponservice.security.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    SecurityService securityService;
    public final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;




    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")

    public String login(String email, String password) {
        boolean login = securityService.login(email, password);
        if (login) {
            return "index";
        } else return "login";
    }

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/showReg")
    public String showRegisterationPage() {
        return "registerUser";
    }

    @PostMapping("/registerUser")
    public String registerUser(User user,
                               @RequestParam(defaultValue = "false")  boolean ADMIN,
                               @RequestParam(defaultValue = "false")  boolean USER) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> user_roles = new HashSet<>();
        if(ADMIN){
            user_roles.add(roleRepo.findByName("ROLE_ADMIN"));
        }
        if(USER){
            user_roles.add(roleRepo.findByName("ROLE_USER"));
        }
        user.setRoles(user_roles);
        userRepo.save(user);
        return "login";
    }
}
