package com.knkweb.yarnshop.service;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.converter.OrderHeaderToQuickOrderCommand;
import com.knkweb.yarnshop.converter.QuickOrderCommandToOrderHeader;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;
import com.knkweb.yarnshop.repositories.OrderHeaderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderHeaderServeceImpl implements OrderHeaderService {

    private final OrderHeaderRepository orderHeaderRepository;
    private final QuickOrderCommandToOrderHeader cmdToObjConverter;
    private final OrderHeaderToQuickOrderCommand objToCmdConverter;

    public OrderHeaderServeceImpl(OrderHeaderRepository orderHeaderRepository,
                                  QuickOrderCommandToOrderHeader cmdToObjConverter,
                                  OrderHeaderToQuickOrderCommand objToCmdConverter) {
        this.orderHeaderRepository = orderHeaderRepository;
        this.cmdToObjConverter = cmdToObjConverter;
        this.objToCmdConverter = objToCmdConverter;
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

    @Override
    public QuickOrderCommand findProductsOfLastStage(long orderId) {
        Optional<OrderHeader> orderHeaderOptional = orderHeaderRepository.findById(orderId);
        return objToCmdConverter.convert(orderHeaderOptional.orElse(null));
    }
}
