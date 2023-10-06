package com.knkweb.yarnshop.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;
import com.knkweb.yarnshop.domain.OrderLine;
import com.knkweb.yarnshop.domain.OrderStatus;
import com.knkweb.yarnshop.repositories.ProductRepository;
import com.knkweb.yarnshop.service.CustomerService;
import com.sun.istack.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuickOrderCommandToOrderHeader implements Converter<QuickOrderCommand, OrderHeader> {

    private final CustomerService customerService;
    private final ProductRepository productRepository;


    public QuickOrderCommandToOrderHeader(CustomerService customerService,
                                          ProductRepository productRepository) {
        this.customerService = customerService;
        this.productRepository = productRepository;
    }

    @Override
    @Synchronized
    @Nullable
    public OrderHeader convert(QuickOrderCommand quickOrderCommand) {
        if (quickOrderCommand == null) {
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> items = null;
        List<Integer> qty = null;
        List<String> units = null;

        try {
            items = objectMapper.readValue(quickOrderCommand.getItemsData(),
                    new TypeReference<List<String>>() {
                    });
            qty = objectMapper.readValue(quickOrderCommand.getQuantitiesData(),
                    new TypeReference<List<Integer>>() {
                    });
            units = objectMapper.readValue(quickOrderCommand.getUnitsData(),
                    new TypeReference<List<String>>() {
                    });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println("--".repeat(50));

        System.out.println(items.toString());
        System.out.println(qty.toString());
        System.out.println(units.toString());

        System.out.println("---".repeat(50));
        Customer customerCheck = customerService.findById(quickOrderCommand.getCustomerId());
        OrderHeader orderHeader = OrderHeader.builder()
                .customer(customerService.findById(quickOrderCommand.getCustomerId()))
                .levels(quickOrderCommand.getLevels())
                .orderStatus("in_progress")
                .build();
        OrderLine[] orderLines = generateOrderLines(quickOrderCommand.getLevels(), items, qty,
                units);
        for (OrderLine o : orderLines
        ) {
            orderHeader.addOrderLine(o);
        }

        return orderHeader;
    }

    private OrderLine[] generateOrderLines(Integer level, List<String> items, List<Integer> qty,
                                           List<String> units) {
        OrderLine[] orderLines = new OrderLine[items.size()];
        for (int i = 0; i < items.size(); i++) {
            orderLines[i] = OrderLine.builder()
                    .product(productRepository.findByDescription(items.get(i)))
                    .quantityOrdered(qty.get(i))
                    .unit(units.get(i))
                    .level(level)
                    .status("in_Progress")
                    .build();
        }
        return orderLines;
    }
}
