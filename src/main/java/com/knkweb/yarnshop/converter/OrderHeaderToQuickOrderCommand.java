package com.knkweb.yarnshop.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;
import com.knkweb.yarnshop.domain.OrderLine;
import com.knkweb.yarnshop.repositories.ProductRepository;
import com.knkweb.yarnshop.service.CustomerService;
import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.List;


@Component
public class OrderHeaderToQuickOrderCommand implements Converter<OrderHeader, QuickOrderCommand> {

    private final CustomerService customerService;
    private final ProductRepository productRepository;


    public OrderHeaderToQuickOrderCommand(CustomerService customerService,
                                          ProductRepository productRepository) {
        this.customerService = customerService;
        this.productRepository = productRepository;
    }

    @Override
    @Synchronized
    @Nullable
    public QuickOrderCommand convert(OrderHeader orderHeader) {
        if (orderHeader == null) {
            return null;
        }

        QuickOrderCommand quickOrderCommand;
        quickOrderCommand = QuickOrderCommand.builder()
                .levels(orderHeader.getLevels())
                .orderHeaderId(orderHeader.getId())
                .orderStatus(orderHeader.getOrderStatus())
                .customerId(orderHeader.getCustomer().getId())
                .build();
        for (OrderLine l: orderHeader.getOrderLines()
             ) {
            if(l.getLevel() == quickOrderCommand.getLevels()){
                quickOrderCommand.getItemsList().add(l.getProduct().getDescription());
            }
        }

        return quickOrderCommand;
    }
}
