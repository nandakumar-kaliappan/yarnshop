package com.knkweb.yarnshop.repositories;

import com.knkweb.yarnshop.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void findByCustomerName() {
        Customer customerSai = customerRepository.findByCustomerName("Sai Textile").get();
        assertThat(customerSai).isNotNull();
        assertThat(customerSai.getCustomerName()).isEqualTo("Sai Textile");
    }

    @Test
    void findByUser() {
        Customer customerSai = customerRepository.findByCustomerName("Sai Textile").get();
        Customer customerTest = customerRepository.findByUser(customerSai.getUser()).get();
        assertThat(customerSai).isEqualTo(customerTest);

    }


}