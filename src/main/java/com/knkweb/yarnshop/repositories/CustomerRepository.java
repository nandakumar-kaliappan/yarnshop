package com.knkweb.yarnshop.repositories;


import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUser(User user);
    Optional<Customer> findByCustomerName(String customerName);

}
