package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;

import java.util.List;

public interface OrderHeaderService {

    void saveOrUpdate(QuickOrderCommand quickOrderCommand);

    List<OrderHeader> findAllOrders();

    List<OrderHeader> findOrders(Customer customer);

    QuickOrderCommand findProductsOfLastStage(long orderId);

    void closeOrder(long orderId);

    OrderHeader findOrderByOrderId(long parseLong);
}
