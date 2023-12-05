package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.command.CustomerCommand;
import com.knkweb.yarnshop.domain.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    Customer findById(Long customer);

    Page<Customer> findAll(int page);

    void saveOrUpdate(CustomerCommand customerCommand);
}
