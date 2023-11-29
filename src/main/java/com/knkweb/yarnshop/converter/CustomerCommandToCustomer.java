package com.knkweb.yarnshop.converter;

import com.knkweb.yarnshop.command.CustomerCommand;
import com.knkweb.yarnshop.domain.Address;
import com.knkweb.yarnshop.domain.Customer;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerCommandToCustomer implements Converter<CustomerCommand, Customer> {
    @Override
    public Customer convert(CustomerCommand command) {
        Customer customer = Customer.builder()
                .userName(command.getUserName())
                .customerName(command.getCustomerName())
                .phone(command.getPhone())
                .user(command.getUser())
                .address(Address.builder().address(command.getAddress()).build())
                .email(command.getEmail())
                .build();
        customer.setId(command.getId());
        return customer;
    }
}
