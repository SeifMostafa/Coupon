package com.musala.couponservice;

import com.musala.couponservice.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
//        http.authorizeRequests().
//                mvcMatchers(HttpMethod.GET, "/couponapi/coupons/**", "/index",
//                        "/showGetCoupon", "/couponDetails").permitAll().
////                hasAnyRole("USER", "ADMIN").
//                mvcMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole("USER", "ADMIN").
//                mvcMatchers(HttpMethod.GET, "/saveCoupon", "/showCreateCoupon", "/createResponse").hasAnyRole("ADMIN").
//                mvcMatchers(HttpMethod.POST, "/couponapi/coupons", "/saveCoupon")
//                .hasAnyRole("ADMIN").
//                mvcMatchers("/", "/login", "/logout", "/showReg", "/registerUser").permitAll().
//                anyRequest().denyAll().and().logout().logoutSuccessUrl("/");

//        http.csrf(csrfCustomizer -> {
//
//            csrfCustomizer.ignoringAntMatchers("/couponapi/coupons/**");
//            RequestMatcher requestMatcher = new RegexRequestMatcher("/couponapi/coupons/{code:^[A-Z]*$}", "POST");
//            requestMatcher = new MvcRequestMatcher(new HandlerMappingIntrospector(),"/getCoupon");
//            csrfCustomizer.ignoringRequestMatchers(requestMatcher);
//        });
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}