package com.knkweb.yarnshop.controller;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.Product;
import com.knkweb.yarnshop.repositories.CustomerRepository;
import com.knkweb.yarnshop.repositories.ProductRepository;
import com.knkweb.yarnshop.service.CustomerService;
import com.knkweb.yarnshop.service.OrderHeaderService;
import com.knkweb.yarnshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    public static final String ADMIN_MAX_ROLE = "adminTest";
    public static final String USERNAME_MANAGER = "manager";
    public static final List<Customer> CUSTOMERS = new ArrayList<>();
    public static final List<Product> PRODUCTS = new ArrayList<>();

    @MockBean
    ProductRepository productRepository;
    @MockBean
    CustomerRepository customerRepository;
    @MockBean
    OrderHeaderService orderHeaderService;
    @MockBean
    UserService userService;

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        when(userService.findMaxRole(any())).thenReturn(ADMIN_MAX_ROLE);
        when(productRepository.findAll()).thenReturn(PRODUCTS);
        CUSTOMERS.add(Customer.builder().customerName("C1").build());
        CUSTOMERS.add(Customer.builder().customerName("C2").build());
        when(customerRepository.findAll()).thenReturn(CUSTOMERS);
    }


    @Test
    @WithMockUser(username = USERNAME_MANAGER, roles = {"ADMIN"})
    void ordersAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/quick-order"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("username", USERNAME_MANAGER))
                .andExpect(MockMvcResultMatchers.model().attribute("topRole", ADMIN_MAX_ROLE))
                .andExpect(MockMvcResultMatchers.model().attribute("products", PRODUCTS))
                .andExpect(MockMvcResultMatchers.model().attribute("customers", CUSTOMERS))
                .andExpect(MockMvcResultMatchers.model().attribute("quickOrderCommand", isA(QuickOrderCommand.class)))
                .andExpect(MockMvcResultMatchers.view().name("customer/quick-order"));
    }

    @Test
    @WithMockUser(username = USERNAME_MANAGER, roles = {"CUSTOMER"})
    void placeOrderRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/quick-order/place")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .content("orderHeaderId=5&itemsData=Someitem"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/auth/orderslist"));
    }
}