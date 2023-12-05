package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;
import com.knkweb.yarnshop.repositories.OrderHeaderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderHeaderServeceImplTest {
    @Mock
    OrderHeaderRepository orderHeaderRepository;
    @InjectMocks
    OrderHeaderServeceImpl orderHeaderServece;

    @BeforeEach
    void setup(){

    }

    @Test
    void findAllOrders() {
        ArgumentCaptor<Pageable> argumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        when(orderHeaderRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        orderHeaderServece.findAllOrders(6);

        verify(orderHeaderRepository,times(1)).findAll(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getPageSize()).isEqualTo(5);
        assertThat(argumentCaptor.getValue().getPageNumber()).isEqualTo(6);
    }

    @Test
    void findOrders() {
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        when(orderHeaderRepository.findAllByCustomer(any(Customer.class),
                any(Pageable.class))).thenReturn(Page.empty());

        orderHeaderServece.findOrders(Customer.builder().customerName("TestCustomer").build(),6);

        verify(orderHeaderRepository,times(1)).findAllByCustomer(argumentCaptor.capture(),any());
        assertThat(argumentCaptor.getValue().getClass()).isEqualTo(Customer.class);
        assertThat(argumentCaptor.getValue().getCustomerName()).isEqualTo("TestCustomer");
    }
}