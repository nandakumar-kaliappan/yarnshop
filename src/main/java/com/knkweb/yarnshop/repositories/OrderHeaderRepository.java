package com.knkweb.yarnshop.repositories;


import com.knkweb.yarnshop.domain.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {
}
