package com.knkweb.yarnshop.controller;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.domain.User;
import com.knkweb.yarnshop.repositories.CustomerRepository;
import com.knkweb.yarnshop.repositories.ProductRepository;
import com.knkweb.yarnshop.service.OrderHeaderService;
import com.knkweb.yarnshop.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class OrderController {

    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final OrderHeaderService orderHeaderService;
    private final UserService userService;

    public OrderController(ProductRepository productRepository,
                           CustomerRepository customerRepository,
                           OrderHeaderService orderHeaderService, UserService userService) {
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.orderHeaderService = orderHeaderService;
        this.userService = userService;
    }

    @RequestMapping("/admin/quick-order")
    public String ordersAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("quick order page");
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("topRole", userService.findMaxRole(userDetails));
        Long customerId = 3l; //todo update needed here. replace the hardcoded value;
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("customers", customerRepository.findAll());
        model.addAttribute("customerId", null);
        model.addAttribute("quickOrderCommand", new QuickOrderCommand());
        return "customer/quick-order";
    }

    @RequestMapping("/admin/quick-order/place")
    public String placeOrderRequest(Model model,
                                    @ModelAttribute QuickOrderCommand quickOrderCommand,
                                    @AuthenticationPrincipal UserDetails userDetails
            , @RequestParam("itemsData") String itemsData,
                                    @RequestParam("quantitiesData") String quantitiesData,
                                    @RequestParam(
            "unitsData") String unitsData) {
        System.out.println("Order placed");
        System.out.println(itemsData + " \n " + quantitiesData + " \n" + unitsData);
        System.out.println("__".repeat(5));
        System.out.println(quickOrderCommand);
        orderHeaderService.saveOrUpdate(quickOrderCommand);
        return "redirect:/auth/orderslist";
    }

    @RequestMapping("/auth/orderslist")
    public String viewAllOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("Orders list");
        String topRole = userService.findMaxRole(userDetails);
        model.addAttribute("topRole", topRole);
        model.addAttribute("username",userDetails.getUsername());

        if (topRole.equals("customer")) {

            User user = userService.findUser(userDetails);
            System.out.println("__".repeat(50));
            System.out.println(user.getUsername());
            System.out.println("TopRole: "+topRole);
            System.out.println("Customer Requesting order");
            System.out.println(user);
            System.out.println(user.getCustomer());
            System.out.println("__".repeat(50));

            model.addAttribute("orders", orderHeaderService.findOrders(user.getCustomer()));
        } else {
            System.out.println("__".repeat(50));
            System.out.println("TopRole: "+topRole);
            System.out.println("Admin Requesting order");
            System.out.println("__".repeat(50));
            model.addAttribute("orders", orderHeaderService.findAllOrders());
        }
        return "authenticated/orders-list";
    }
}
