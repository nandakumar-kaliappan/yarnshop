package com.knkweb.yarnshop.converter;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.domain.OrderHeader;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuickOrderCommandToOrderHeaderIT {
    @Autowired
    QuickOrderCommandToOrderHeader converter;
    @Test
    void convertTest() {
        JSONArray arrayi = new JSONArray();
        arrayi.put("W113");
        arrayi.put("W116");
        arrayi.put("W117");
        JSONArray arrayq = new JSONArray();
        arrayq.put(1);
        arrayq.put(3);
        arrayq.put(1);
        JSONArray arrayu = new JSONArray();
        arrayu.put("Kg");
        arrayu.put("Kg");
        arrayu.put("Each");

        String i = arrayi.toString();
        String q = arrayq.toString() ;
        String u =  arrayu.toString();
        QuickOrderCommand quickOrderCommand = QuickOrderCommand.builder()
                .customerId(10L)
                .itemsData(i)
                .levels(1)
                .quantitiesData(q)
                .unitsData(u)
                .build();
        OrderHeader orderHeader = converter.convert(quickOrderCommand);
        assertNotNull(orderHeader);

    }
}