package com.knkweb.yarnshop.controller;

import com.knkweb.yarnshop.domain.User;
import com.knkweb.yarnshop.service.UserService;
import com.knkweb.yarnshop.service.UserServiceImpl;
import org.hamcrest.core.StringContains;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNull;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerAuthorizationTest {




    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    @WithMockUser(username = "manager", roles = "ADMIN")
    void adminstrationAdminWithAdmin() throws Exception {
        System.out.println("test begins");
        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/admin"))
                .andExpect(MockMvcResultMatchers.model().attribute("username","manager"))
                .andExpect(MockMvcResultMatchers.model().attribute("topRole","admin"))
                .andExpect(MockMvcResultMatchers.model().attribute("customers", isA(ArrayList.class)));

    }

    @Test
    @WithMockUser(username = "saitextile", roles = "CUSTOMER")
    void adminstrationAdminWithCustomer() throws Exception {
        System.out.println("test begins");
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/admin"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void indexPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("all/login"))
                .andExpect(MockMvcResultMatchers.model().attribute("topRole","all"));
    }

    @Test
    @WithMockUser(username = "manager", roles = "ADMIN")
    void welcomeAdminWithAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("all/products"))
                .andExpect(MockMvcResultMatchers.model().attribute("username","manager"))
                .andExpect(MockMvcResultMatchers.model().attribute("topRole","admin"));
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("all/products"))
                .andExpect(MockMvcResultMatchers.model().attribute("username","manager"))
                .andExpect(MockMvcResultMatchers.model().attribute("topRole","admin"));
    }

    @Test
    @WithMockUser(username = "manager", roles = "CUSTOMER")
    void welcomeAdminWithCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/index"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void welcomeAdminWithAnonymousUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(MockMvcResultMatchers.status().is(302));
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/index"))
                .andExpect(MockMvcResultMatchers.status().is(302));
    }

    @Test
    @WithMockUser(username = "manager", roles = "ADMIN")
    void aboutMeAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/aboutme"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("admin/aboutme"))
                .andExpect(MockMvcResultMatchers.model().attribute("username","manager"))
                .andExpect(MockMvcResultMatchers.model().attribute("topRole","admin"));
    }



    @Test
    @WithMockUser(username = "saitextiles", roles = {"CUSTOMER"})
    void welcomeCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/index"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("all/products"))
                .andExpect(MockMvcResultMatchers.model().attribute("username","saitextiles"))
                .andExpect(MockMvcResultMatchers.model().attribute("topRole","customer"));
    }

    @Test
    @WithMockUser(username = "saitextiles", roles = {"CUSTOMER"})
    void aboutMeCustomer() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/customer/aboutme"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("customer/aboutme"))
            .andExpect(MockMvcResultMatchers.model().attribute("username","saitextiles"))
            .andExpect(MockMvcResultMatchers.model().attribute("topRole","customer"));

    }


}