package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    Customer customer = Customer.builder().customerName("TestCustomer").build();
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
    void testFindAll() {
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);
        when(customerRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl(customers));

        Page<Customer> customerPage = customerService.findAll(0);

        Customer customerReturned = customerPage.getContent().get(0);
        assertThat(customerReturned).isEqualTo(customer);
        verify(customerRepository,times(1)).findAll(any(Pageable.class));
        verifyNoMoreInteractions(customerRepository);
    }
}