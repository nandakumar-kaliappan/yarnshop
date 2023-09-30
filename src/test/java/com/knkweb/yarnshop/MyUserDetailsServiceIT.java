package com.knkweb.yarnshop;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan({"com.knkweb.yarnshop.security"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MyUserDetailsServiceIT {
    @Autowired
    UserDetailsService userDetailsService;
    @Test
    void testPresentUser(){

        UserDetails userDetails = userDetailsService.loadUserByUsername("sai");
        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(),"sai");
        assertEquals(userDetails.getPassword(),"passS");
        assertEquals(userDetails.getAuthorities().iterator().next().getAuthority(),"CUSTOMER");

        UserDetails userDetails1 = userDetailsService.loadUserByUsername("manager");
        assertNotNull(userDetails1);
        assertEquals(userDetails1.getUsername(),"manager");
        assertEquals(userDetails1.getPassword(),"passM");
        assertEquals(userDetails1.getAuthorities().iterator().next().getAuthority(),"MANAGER");

    }

    @Test
    void testNotAvailableUser() {
        UsernameNotFoundException une = assertThrows(UsernameNotFoundException.class,
                () -> {
                    userDetailsService.loadUserByUsername(
                            "ghfjdkslyruei");
                });
        assertEquals(une.getMessage(), "Please Check the entered " +
                "username :(");
    }

}
