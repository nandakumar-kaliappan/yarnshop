package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerServiceImpl customerService;

    Customer customer;
    List<Customer> customers;
    @BeforeEach
    void setup(){
        customer = Customer.builder().customerName("testCustomer").build();
        customers = new ArrayList<Customer>();
        customers.add(customer);
    }
    @Test
    void findById() {
        when(customerRepository.findById(any()))
                .thenReturn(Optional.of(customer));

        Customer customer = customerService.findById(3l);

        assertThat(customer.getCustomerName()).isEqualTo("testCustomer");
        verify(customerRepository,times(1)).findById(anyLong());
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    void findAll() {
        when(customerRepository.findAll())
                .thenReturn(customers);

        Customer customer = customerService.findAll().get(0);

        assertThat(customer.getCustomerName()).isEqualTo("testCustomer");
        verify(customerRepository,times(1)).findAll();
        verifyNoMoreInteractions(customerRepository);
    }
}