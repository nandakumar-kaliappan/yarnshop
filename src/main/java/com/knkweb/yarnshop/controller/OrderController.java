package com.knkweb.yarnshop.controller;

import com.knkweb.yarnshop.repositories.ProductRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {

    private final ProductRepository productRepository;

    public OrderController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RequestMapping("/admin/quick-order")
    public String ordersAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("quick order page");
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("topRole","admin"); //todo refactor this
        Long customerId = 3l; //todo update needed here. replace the hardcoded value;
        model.addAttribute("products",productRepository.findAll());
        model.addAttribute("customerId",customerId);
        return "customer/quick-order";
    }
}
