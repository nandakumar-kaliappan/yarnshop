package com.knkweb.yarnshop.security.config;


import com.knkweb.yarnshop.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.*;

class MyUserDetailTest {
    UserDetails userDetails;
    String customerName = "sai";

    @Test
    void TestLoadUserByName() {
        userDetails = new User();
        assertNotNull(userDetails);
        assertEquals(userDetails.getPassword(), null);
        //assertEquals(userDetails.getAuthorities().iterator().next().getAuthority(),"CUSTOMER");
        //System.out.println(userDetails.getAuthorities().iterator().next().getAuthority());

    }





}