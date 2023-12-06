package com.knkweb.yarnshop.controller;

import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;
import com.knkweb.yarnshop.service.CustomerService;
import com.knkweb.yarnshop.service.OrderHeaderService;
import com.knkweb.yarnshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;
    private final CustomerService customerService;
    private final OrderHeaderService orderHeaderService;

    public UserController(UserService userService, CustomerService customerService,
                          OrderHeaderService orderHeaderService) {
        this.userService = userService;
        this.customerService = customerService;
        this.orderHeaderService = orderHeaderService;
    }

    @RequestMapping({"/","/index","/index.html"})
    public String indexPage(Model model){
        model.addAttribute("username",null);
        model.addAttribute("topRole","all");//todo refactor this
        return "all/products";
    }

    @RequestMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("username",null);
        model.addAttribute("topRole","all");//todo refactor this
        return "all/login";
    }

    @RequestMapping({"/homepage"})
    public String homePage(Model model, @AuthenticationPrincipal UserDetails userDetails){
        log.debug("home page access with max role : "+ userService.findMaxRole(userDetails));
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("topRole",userService.findMaxRole(userDetails));
        return "all/products";
    }
    @RequestMapping({"/admin/index","/admin"})
    public String welcomeAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("admin welcome");
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("topRole",userService.findMaxRole(userDetails));
        System.out.println(userDetails.getUsername());
        return "all/products";
    }
    @RequestMapping("/admin/aboutme")
    public String aboutMeAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("admin aboutme");
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("topRole",userService.findMaxRole(userDetails));
        System.out.println(userDetails.getUsername());
        return "admin/aboutme";
    }

    @GetMapping("/admin/admin")
    public String adminstrationAdmin(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                                     @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("admin page");
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("topRole",userService.findMaxRole(userDetails));
        Page<Customer> customerPage = customerService.findAll(page);
        model.addAttribute("customers",customerPage);
        System.out.println(userDetails.getUsername());
        return "admin/admin";
    }

    @GetMapping("/admin/customer/{customerId}/get")
    public String showCustomerDetails(Model model,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      @PathVariable("customerId") Long customerId,
                                      @RequestParam(value = "page", defaultValue="0") int page
    ){
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("topRole",userService.findMaxRole(userDetails));
        log.debug("customer Details Requested for id : "+ customerId );
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer",customer);
        Page<OrderHeader> orderHeadersPage= orderHeaderService.findOrders(customer, page);
        model.addAttribute("orders", orderHeadersPage);
        log.debug("customer Details Requested for id : "+ customerId );
        orderHeadersPage.getContent().stream()
                .forEach(orderHeader -> {
                    System.out.println("_".repeat(20));
                    System.out.println("ID"+orderHeader.getId());
                    System.out.println(orderHeader.getCustomer().getCustomerName());});
        System.out.println("page Size:"+orderHeadersPage.getSize());
        System.out.println("page Number:"+orderHeadersPage.getNumber());
        System.out.println("pages count:"+orderHeadersPage.getTotalPages());
        System.out.println("Total Elements:"+orderHeadersPage.getTotalElements());

        return "admin/customerdetails";
    }



    @RequestMapping("/customer/index")
    public String welcomeCustomer(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("welcome customer");
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("topRole",userService.findMaxRole(userDetails));
        System.out.println(userDetails.getUsername());
        return "all/products";
    }

    @RequestMapping("/customer/aboutme")
    public String aboutMeCustomer(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("customer aboutme");
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("topRole",userService.findMaxRole(userDetails));
        System.out.println(userDetails.getUsername());
        return "customer/aboutme";
    }
}
