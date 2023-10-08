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

        if (quickOrderCommand.getOrderHeaderId()==null) {
            System.out.println("New Order About to be Placed.");
            OrderHeader orderHeader = cmdToObjConverter.convert(quickOrderCommand);
            orderHeader.setCount(orderHeader.getOrderLines().size());
            orderHeaderRepository.saveAndFlush(orderHeader);
        } else {

            OrderHeader orderHeader1 = cmdToObjConverter.convert(quickOrderCommand);
            System.out.println("--".repeat(50));

            System.out.println("Old Order About to be Re-Placed");
            System.out.println(orderHeader1);

            System.out.println("--".repeat(50));

            OrderHeader orderHeader =
                    orderHeaderRepository.findById(quickOrderCommand.getOrderHeaderId()).get();

            orderHeader.setLevels(orderHeader1.getLevels());
            orderHeader.setCount(orderHeader1.getOrderLines().size());
            orderHeader.setOrderStatus(orderHeader1.getOrderStatus());
            System.out.println("---XXX----");
            orderHeader.getOrderLines().forEach(System.out::println);
            System.out.println("---XXX----");
            orderHeader1.getOrderLines().forEach(orderLine -> {
                System.out.println(orderLine);
                orderHeader.addOrderLine(orderLine);
            });
            orderHeaderRepository.save(orderHeader);

        }
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

    @Override
    @Transactional
    public void closeOrder(long orderId) {
        Optional<OrderHeader> orderHeaderOptional = orderHeaderRepository.findById(orderId);
        OrderHeader orderHeader = orderHeaderOptional.get();
        if(orderHeader.getOrderStatus().equals("closed")){
            orderHeader.setOrderStatus("in_progress");
        }else{
            orderHeader.setOrderStatus("closed");
        }

        orderHeaderRepository.save(orderHeader);
    }

    @Override
    public OrderHeader findOrderByOrderId(long orderId) {
        Optional<OrderHeader> orderHeaderOptional = orderHeaderRepository.findById(orderId);
        return orderHeaderOptional.get();
    }
}
