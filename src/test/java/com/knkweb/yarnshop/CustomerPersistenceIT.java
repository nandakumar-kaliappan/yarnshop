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
        User userak = User.builder().username("akrgroups").password("user").build();
        userak.addAuthorities(Authority.builder().role("CUSTOMER").build());

        customerRepository.saveAndFlush(Customer.builder().customerName("AKR Groups")
                .user(userak)
                .address(Address.builder().address("PN rd, Tiruppur").build())
                .email("akr@gmail.com")
                .phone("8100799150")
                .build());

        assertNotNull(customerRepository.findByCustomerName("AKR Groups").get());
        assertNotNull(userRepository.findByUsername("akrgroups").get());

        assertEquals(customerRepository.findByCustomerName("AKR Groups").get().getCustomerName(),"AKR Groups");
        assertEquals(userRepository.findByUsername("akrgroups").get().getUsername(),"akrgroups");
        assertEquals(userRepository.findByUsername("akrgroups").get().getPassword(),"user");


    }
}
