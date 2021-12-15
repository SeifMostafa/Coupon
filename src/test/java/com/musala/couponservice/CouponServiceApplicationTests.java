package com.musala.couponservice;

import com.musala.couponservice.repos.CouponRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CouponServiceApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CouponRepo couponRepo;

    @Test
    public void testGetCouponWithoutAuth_UnAuth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/couponapi/coupons/3")).andExpect(status().isUnauthorized());
    }
    @Test
    @WithUserDetails("Toka@gmail.com")
    public void testGetCouponWithAuth_Auth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/couponapi/coupons/3")).andExpect(status().isOk());
    }

    @Test
//    @WithMockUser
    @WithUserDetails("john@ferguson.com")
    public void testGetCouponWithAuth_OK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/couponapi/coupons/3")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void testGetCouponContent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/couponapi/coupons/3"))
                .andExpect(status().isOk()).
                andExpect(content().string("{\"id\":4,\"code\":\"3\",\"discount\":100.000,\"expDate\":\"1-1-2022\"}"));
    }

//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    public void testCreateCoupon_WithoutCSRF_Forbidden() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders.post("/couponapi/coupons")
//                        .content("{\"code\":\"100\",\"discount\":10,\"expDate\":\"1-1-2022\"}").contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    public void testCreateCoupon_WitHCSRF_OK() throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders.post("/couponapi/coupons")
//                        .content("{\"code\":\"101\",\"discount\":10,\"expDate\":\"1-1-2022\"}").contentType(MediaType.APPLICATION_JSON).with(csrf().asHeader()))
//                .andExpect(status().isOk());
//    }

//    @Test
//    @WithMockUser
//    public void testCSRF()throws Exception{
//    mockMvc.perform(options("/couponapi/coupons")
//            .header("Access-Control-Request-Method","POST").
//            header("Origin","http://localhost:3000")).andExpect(status().isOk()).andExpect(header().exists("Access-Control-Allow-Origin"))
//            .andExpect(header().string("Access-Control-Allow-Origin","*")).andExpect(header().exists("Access-Control-Allow-Methods"))
//            .andExpect(header().string("Access-Control-Allow-Origin","POST"));
//    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void testCORS() throws Exception{
        mockMvc.perform(options("/couponapi/coupons")
                        .header("Access-Control-Request-Method","POST").
                        header("Origin","http://localhost:3000")).andExpect(status().isOk()).andExpect(header().exists("Access-Control-Allow-Origin"))
                .andExpect(header().string("Access-Control-Allow-Origin","*")).andExpect(header().exists("Access-Control-Allow-Methods"))
                .andExpect(header().string("Access-Control-Allow-Origin","*"));
    }


}
