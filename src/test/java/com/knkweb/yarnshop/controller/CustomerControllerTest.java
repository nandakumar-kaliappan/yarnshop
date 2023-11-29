package com.knkweb.yarnshop.controller;

import com.knkweb.yarnshop.command.CustomerCommand;
import com.knkweb.yarnshop.repositories.CustomerRepository;
import com.knkweb.yarnshop.repositories.ProductRepository;
import com.knkweb.yarnshop.service.CustomerService;
import com.knkweb.yarnshop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;

//    @MockBean
//    UserService userService;
//
//    @MockBean
//    CustomerService customerService;
//    @MockBean
//    ProductRepository productRepository;
//
//   @MockBean
//    CustomerRepository customerRepository;

    @BeforeEach
    void setup() {

    }

    @Test
    @WithMockUser(username = "Manager", roles = {"CUSTOMER", "ADMIN"})
    void addNewCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/customer/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("customerCommand",
                        isA(CustomerCommand.class)))
                .andExpect(MockMvcResultMatchers.view().name("admin/new-customer"));
    }

    @Test
    @WithMockUser(username = "Manager", roles = "ADMIN")
    void saveNewCustomer() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/customer/new")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("userName=senthur&phone=9600"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/admin"));
    }
}