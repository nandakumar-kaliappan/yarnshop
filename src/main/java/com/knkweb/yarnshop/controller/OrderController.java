package com.knkweb.yarnshop.controller;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.domain.Customer;
import com.knkweb.yarnshop.domain.OrderHeader;
import com.knkweb.yarnshop.domain.User;
import com.knkweb.yarnshop.repositories.CustomerRepository;
import com.knkweb.yarnshop.repositories.ProductRepository;
import com.knkweb.yarnshop.service.OrderHeaderService;
import com.knkweb.yarnshop.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
                                    @AuthenticationPrincipal UserDetails userDetails) {

        orderHeaderService.saveOrUpdate(quickOrderCommand);
        return "redirect:/auth/orderslist";
    }

    @RequestMapping("/auth/orderslist")
    public String viewAllOrders(Model model,
                                @RequestParam(value = "page",defaultValue = "0") int page,
                                @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("Orders list");
        String topRole = userService.findMaxRole(userDetails);
        model.addAttribute("topRole", topRole);
        model.addAttribute("username", userDetails.getUsername());

        if (topRole.equals("customer")) {

            User user = userService.findUser(userDetails);
            System.out.println("__".repeat(50));
            System.out.println(user.getUsername());
            System.out.println("TopRole: " + topRole);
            System.out.println("Customer Requesting order");
            System.out.println(user);
            System.out.println(user.getCustomer());
            System.out.println("__".repeat(50));

            model.addAttribute("orders", orderHeaderService.findOrders(user.getCustomer(),page));
        } else {
            System.out.println("__".repeat(50));
            System.out.println("TopRole: " + topRole);
            System.out.println("Admin Requesting order");
            System.out.println("__".repeat(50));
            Page<OrderHeader> page1 = orderHeaderService.findAllOrders(page);
            System.out.println(page1.getContent().size());
            model.addAttribute("orders",page1);
        }
        return "authenticated/orders-list";
    }

    @RequestMapping("/auth/{orderId}/suborder")
    public String placeOrderRequest(Model model,
                                    @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable String orderId) {

        String topRole = userService.findMaxRole(userDetails);
        QuickOrderCommand quickOrderCommand1 =
                orderHeaderService.findProductsOfLastStage(Long.parseLong(orderId));
        model.addAttribute("quickOrderCommand", quickOrderCommand1);
        System.out.println("__".repeat(50));
        System.out.println("sub order request received for: " + orderId);
        System.out.println("Admin Requesting order");
        System.out.println("quick order passed to form:");
        System.out.println(quickOrderCommand1);
        System.out.println("__".repeat(50));
        model.addAttribute("topRole", topRole);
        model.addAttribute("username", userDetails.getUsername());

        return "authenticated/suborder";
    }
    @RequestMapping("/auth/{orderId}/closeoropen")
    public String closeOrderRequest(Model model,
                                    @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable String orderId) {

        String topRole = userService.findMaxRole(userDetails);

        System.out.println("__".repeat(50));
        System.out.println("Close order request received for: " + orderId);
        System.out.println("__".repeat(50));
        orderHeaderService.closeOrder(Long.parseLong(orderId));
        return "redirect:/auth/orderslist";
    }
    @RequestMapping("/auth/auth/{orderId}/viewdetail")
    public String viewOrderDetail(Model model,
                                    @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable String orderId) {
        String topRole = userService.findMaxRole(userDetails);
        OrderHeader orderHeader = orderHeaderService.findOrderByOrderId(Long.parseLong(orderId));

        System.out.println("__".repeat(50));
        System.out.println("View detail - request received for: " + orderId);
        System.out.println("order passed to form:");
        System.out.println(orderHeader);
        System.out.println("__.".repeat(50));
        model.addAttribute("topRole", topRole);
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("o", orderHeader);

        return "authenticated/vieworderdetail";

    }

    @RequestMapping("/admin/quick-order/replace")
    public String replaceOrderRequest(Model model,
                                      @ModelAttribute QuickOrderCommand quickOrderCommand,
                                      @AuthenticationPrincipal UserDetails userDetails
            , @RequestParam("itemsData") String itemsData,
                                      @RequestParam("quantitiesData") String quantitiesData,
                                      @RequestParam(
                                              "unitsData") String unitsData) {
        System.out.println("__".repeat(50));
        System.out.println("Order Re- placed");
        System.out.println(itemsData + " \n " + quantitiesData + " \n" + unitsData);
        System.out.println(quickOrderCommand);
        System.out.println("___".repeat(50));
        orderHeaderService.saveOrUpdate(quickOrderCommand);
        return "redirect:/auth/orderslist";
    }

}
