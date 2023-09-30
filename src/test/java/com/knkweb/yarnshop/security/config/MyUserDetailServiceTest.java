package com.knkweb.yarnshop.security.config;

import com.knkweb.yarnshop.repositories.AuthorityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

class MyUserDetailServiceTest {
    UserDetails userDetails;
    String customerName = "sai";
    @Autowired
    AuthorityRepository authorityRepository;
    @Test
    void TestLoadUserByName(){
        userDetails = new MyUserDetailService(authorityRepository).loadUserByUsername(customerName);
        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(),customerName);
        assertEquals(userDetails.getPassword(),"pa");
        //assertEquals(userDetails.getAuthorities().iterator().next().getAuthority(),"CUSTOMER");
        //System.out.println(userDetails.getAuthorities().iterator().next().getAuthority());

    }


}