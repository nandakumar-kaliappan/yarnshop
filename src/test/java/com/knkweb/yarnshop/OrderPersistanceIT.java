package com.knkweb.yarnshop;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.converter.OrderHeaderToQuickOrderCommand;
import com.knkweb.yarnshop.domain.OrderHeader;
import com.knkweb.yarnshop.domain.OrderLine;
import com.knkweb.yarnshop.repositories.OrderHeaderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderPersistanceIT {
    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    OrderHeaderToQuickOrderCommand objToCmdConverter;


    @Test
    void testUpdateOrderLine() {
        OrderHeader orderHeader = orderHeaderRepository.findById(1L).get();
        QuickOrderCommand quickOrderCommand = objToCmdConverter.convert(orderHeader);
        assertNotNull(quickOrderCommand);
        quickOrderCommand.setLevels(2);
        OrderLine orderLine = orderHeader.getOrderLines().iterator().next();
        String prod = orderLine.getProduct().getDescription();
    }
}
