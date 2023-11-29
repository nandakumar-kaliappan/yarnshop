package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.command.CustomerCommand;
import com.knkweb.yarnshop.converter.CustomerCommandToCustomer;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerCommandToCustomer customerCommandToCustomer;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               CustomerCommandToCustomer customerCommandToCustomer) {
        this.customerRepository = customerRepository;
        this.customerCommandToCustomer = customerCommandToCustomer;
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

    @Override
    public void saveOrUpdate(CustomerCommand customerCommand) {

        customerRepository.saveAndFlush(customerCommandToCustomer.convert(customerCommand));
    }
}
