package com.knkweb.yarnshop.repositories;


import com.knkweb.yarnshop.domain.OrderApproval;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderApprovalRepository extends JpaRepository<OrderApproval, Long> {
}
