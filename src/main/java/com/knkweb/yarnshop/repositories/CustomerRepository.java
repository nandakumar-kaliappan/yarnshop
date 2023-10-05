package com.knkweb.yarnshop.repositories;


import com.knkweb.yarnshop.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
