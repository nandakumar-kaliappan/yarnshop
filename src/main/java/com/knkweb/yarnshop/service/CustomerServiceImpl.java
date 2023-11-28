package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findById(Long customer) {
        if (customer == null){
            return null;
        }
        Optional<Customer> customerOptional = customerRepository.findById(customer);
        return customerOptional.orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}
