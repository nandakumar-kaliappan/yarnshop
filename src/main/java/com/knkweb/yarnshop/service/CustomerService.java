package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.domain.Customer;

public interface CustomerService {
    Customer findById(Long customer);
}
