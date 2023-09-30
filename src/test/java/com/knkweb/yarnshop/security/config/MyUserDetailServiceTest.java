package com.knkweb.yarnshop.security.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class MyUserDetailServiceTest {
    UserDetails userDetails;
    String customerName = "sai";
    @Test
    void TestLoadUserByName(){
        userDetails = new MyUserDetailService().loadUserByUsername(customerName);
        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(),customerName);
        assertEquals(userDetails.getPassword(),"passS");
        //assertEquals(userDetails.getAuthorities().iterator().next().getAuthority(),"CUSTOMER");
        //System.out.println(userDetails.getAuthorities().iterator().next().getAuthority());

    }


}