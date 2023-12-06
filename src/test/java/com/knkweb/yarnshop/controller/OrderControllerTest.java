package com.knkweb.yarnshop.controller;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;
import com.knkweb.yarnshop.domain.Product;
import com.knkweb.yarnshop.domain.User;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    public static final String ADMIN_MAX_ROLE = "admin";
    public static final String USERNAME_MANAGER = "manager";
    public static final List<Customer> CUSTOMERS = new ArrayList<>();
    public static final List<Product> PRODUCTS = new ArrayList<>();
    public static final List<OrderHeader> ORDER_HEADERS = new ArrayList<>();

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
        when(userService.findUser(any())).thenReturn(User.builder().username("TestUser").customer(Customer.builder().customerName("Test User").build()).build());

        when(userService.findMaxRole(any())).thenReturn(ADMIN_MAX_ROLE);
        when(productRepository.findAll()).thenReturn(PRODUCTS);
        Customer c1 = Customer.builder().customerName("C1").build();
        CUSTOMERS.add(c1);
        Customer c2 = Customer.builder().customerName("C2").build();
        CUSTOMERS.add(c2);
        when(customerRepository.findAll()).thenReturn(CUSTOMERS);
        ORDER_HEADERS.add(OrderHeader.builder().customer(c1).build());
        ORDER_HEADERS.add(OrderHeader.builder().customer(c2).build());
        when(orderHeaderService.findAllOrders(anyInt())).thenReturn(new PageImpl(ORDER_HEADERS));
        when(orderHeaderService.findOrders(any(Customer.class),anyInt())).thenReturn(new PageImpl(ORDER_HEADERS));
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

//    @Test
//    @WithMockUser(username = USERNAME_MANAGER, roles = {"CUSTOMER"})
//    void placeOrderRequest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/admin/quick-order/place")
//        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//        .content("orderHeaderId=5&itemsData=Someitem"))
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.view().name("redirect:/auth/orderslist"));
//    }

    @Test
    @WithMockUser(username = USERNAME_MANAGER, roles = {"ADMIN"})
    void viewAllOrders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/orderslist"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("authenticated/orders-list"))
                .andExpect(MockMvcResultMatchers.model().attribute("orders",isA(Page.class)));
    }

    @Test
    @WithMockUser(username = "Sai Textiles", roles = {"CUSTOMER"})
    void viewOrdersByCustomers() throws Exception {
        when(userService.findMaxRole(any())).thenReturn("customer");
        mockMvc.perform(MockMvcRequestBuilders.get("/auth/orderslist"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.view().name("authenticated/orders-list"))
                .andExpect(MockMvcResultMatchers.model().attribute("orders",isA(Page.class)));
    }
    @Test
    @WithMockUser(username = USERNAME_MANAGER, roles = {"ADMIN"})
    void placeOrderRequestValidationFailure() throws Exception {
        ConstraintViolationException constraintViolationException = null;
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/admin/quick-order/place")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .content("orderHeaderId=5&itemsData=Someitem"))
                    .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                    .andExpect(MockMvcResultMatchers.view().name("redirect:/auth/orderslist"));
        } catch (NestedServletException nse) {
            constraintViolationException = (ConstraintViolationException) nse.getCause();
        }
        assertThat(constraintViolationException).isNull();
//        assertThat(constraintViolationException.getConstraintViolations().size()).isEqualTo(1);
    }
}