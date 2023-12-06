package com.knkweb.yarnshop.controller;


import com.knkweb.yarnshop.command.CustomerCommand;
import com.knkweb.yarnshop.service.CustomerService;
import com.knkweb.yarnshop.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {
    private final UserService userService;
    private final CustomerService customerService;


    public CustomerController(UserService userService, CustomerService customerService) {
        this.userService = userService;

        this.customerService = customerService;
    }

    @GetMapping("/admin/customer/new")
    public String getNewCustomer(Model model, @AuthenticationPrincipal UserDetails userDetails){
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("topRole", userService.findMaxRole(userDetails));
        model.addAttribute("customerCommand", new CustomerCommand());
        return "admin/new-customer";
    }
    @PostMapping("/admin/customer/new")
    public String saveNewCustomer(Model model, @AuthenticationPrincipal UserDetails userDetails,
                                  @ModelAttribute CustomerCommand customerCommand){
        customerService.saveOrUpdate(customerCommand);
        return "redirect:/admin/admin";
    }

}
