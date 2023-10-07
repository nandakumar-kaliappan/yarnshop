package com.knkweb.yarnshop;

import com.knkweb.yarnshop.domain.Address;
import com.knkweb.yarnshop.domain.Authority;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.User;
import com.knkweb.yarnshop.repositories.CustomerRepository;
import com.knkweb.yarnshop.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerPersistenceIT {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void persisitCustomer() {
        User userbp = User.builder().username("bluepinkap").password("user").build();

        Customer bluePinkAp = Customer.builder().customerName("Blue Pink Apparels")
                .address(Address.builder().address("PN rd, Tiruppur").build())
                .email("akr@gmail.com")
                .phone("8100799150")
                .build();
        bluePinkAp.setUser(userbp);
        customerRepository.saveAndFlush(bluePinkAp);

        User user = userRepository.findByUsername("bluepinkap").get();
        assertNotNull(user);
        
        assertEquals(user.getUsername(),"bluepinkap");
        assertEquals(user.getPassword(),"user");


    }
}
