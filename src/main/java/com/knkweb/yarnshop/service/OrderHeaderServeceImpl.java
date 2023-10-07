package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.converter.QuickOrderCommandToOrderHeader;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;
import com.knkweb.yarnshop.repositories.OrderHeaderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderHeaderServeceImpl implements OrderHeaderService {

    private final OrderHeaderRepository orderHeaderRepository;
    private final QuickOrderCommandToOrderHeader cmdToObjConverter;

    public OrderHeaderServeceImpl(OrderHeaderRepository orderHeaderRepository,
                                  QuickOrderCommandToOrderHeader cmdToObjConverter) {
        this.orderHeaderRepository = orderHeaderRepository;
        this.cmdToObjConverter = cmdToObjConverter;
    }

    @Override
    @Transactional
    public void saveOrUpdate(QuickOrderCommand quickOrderCommand) {

        OrderHeader orderHeader = cmdToObjConverter.convert(quickOrderCommand);
        orderHeaderRepository.saveAndFlush(orderHeader);
    }

    @Override
    public List<OrderHeader> findAllOrders() {
        return orderHeaderRepository.findAll();
    }

    @Override
    public List<OrderHeader> findOrders(Customer customer) {
        return orderHeaderRepository.findAllByCustomer(customer);
    }
}
