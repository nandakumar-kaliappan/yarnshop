package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.command.CustomerCommand;
import com.knkweb.yarnshop.domain.Customer;

import java.util.List;

public interface CustomerService {
    Customer findById(Long customer);

    List<Customer> findAll();

    void saveOrUpdate(CustomerCommand customerCommand);
}
