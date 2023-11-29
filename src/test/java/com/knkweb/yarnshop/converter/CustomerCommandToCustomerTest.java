package com.knkweb.yarnshop.converter;

import com.knkweb.yarnshop.command.CustomerCommand;
import com.knkweb.yarnshop.domain.Address;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CustomerCommandToCustomerTest {
    CustomerCommandToCustomer converter = new CustomerCommandToCustomer();
    String myAddress = "RGP";
    final User myUser = User.builder().username("testuser").build();

    @Test
    void convertEmpty() {
        CustomerCommand command = CustomerCommand.builder()
                .build();
        Customer customer = converter.convert(command);
        assertThat(customer.getId()).isNull();
        assertThat(customer.getUser()).isNull();
        assertThat(customer.getUserName()).isNull();
        assertThat(customer.getAddress()).isNull();
        assertThat(customer.getEmail()).isNull();
    }
    @Test
    void convert() {
        CustomerCommand command = CustomerCommand.builder()
                .customerName("testC")
                .address(myAddress)
                .user(myUser)
                .id(3L)
                .build();
        Customer customer = converter.convert(command);
        assertThat(customer.getId()).isEqualTo(3L);
        assertThat(customer.getUser()).isEqualTo(myUser);
        assertThat(customer.getUserName()).isNull();
        assertThat(customer.getAddress().getAddress()).isEqualTo(myAddress);
        assertThat(customer.getCustomerName()).isEqualTo("testC");
    }
}