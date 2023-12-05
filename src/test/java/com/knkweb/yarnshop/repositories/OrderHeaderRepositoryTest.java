package com.knkweb.yarnshop.repositories;

import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;
import org.apache.catalina.LifecycleState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderHeaderRepositoryTest {

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void findAllWithPageable() {
        List<OrderHeader> orderHeaderList =
                orderHeaderRepository.findAll(PageRequest.of(0, 5)).getContent();
        List<OrderHeader> orderHeaderList1 =
                orderHeaderRepository.findAll(PageRequest.of(1, 5)).getContent();
        assertThat(orderHeaderList.size()).isEqualTo(5);
    }

    @Test
    void findAllByCustomer() {
        Customer customerSai = customerRepository.findByCustomerName("Sai Textile").get();
        assertThat(customerSai).isNotNull();
        assertThat(customerSai.getCustomerName()).isEqualTo("Sai Textile");

        Page<OrderHeader> orderHeaderList =
                orderHeaderRepository.findAllByCustomer(customerSai, PageRequest.of(0,5));

        assertThat(orderHeaderList.getContent().get(0)).isNotNull();
        for (OrderHeader oh : orderHeaderList.getContent()) {
            assertThat(oh.getCustomer().getCustomerName()).isEqualTo("Sai Textile");
            System.out.println("found one");
        }

    }
}