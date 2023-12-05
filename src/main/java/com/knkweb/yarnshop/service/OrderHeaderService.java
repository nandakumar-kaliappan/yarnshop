package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderHeaderService {

    void saveOrUpdate(QuickOrderCommand quickOrderCommand);

    Page<OrderHeader> findAllOrders(int page);

    Page<OrderHeader> findOrders(Customer customer, int page);

    QuickOrderCommand findProductsOfLastStage(long orderId);

    void closeOrder(long orderId);

    OrderHeader findOrderByOrderId(long parseLong);
}
