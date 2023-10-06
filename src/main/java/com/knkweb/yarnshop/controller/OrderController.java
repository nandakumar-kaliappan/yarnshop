package com.knkweb.yarnshop.controller;

import com.knkweb.yarnshop.command.QuickOrderCommand;
import com.knkweb.yarnshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("quickOrderCommand", new QuickOrderCommand());
        return "customer/quick-order";
    }

    @RequestMapping("/admin/quick-order/place")
    public String placeOrderRequest(Model model, @ModelAttribute QuickOrderCommand quickOrderCommand,
                                    @AuthenticationPrincipal UserDetails userDetails
            , @RequestParam("itemsData") String itemsData,
                                    @RequestParam("quantitiesData") String quantitiesData, @RequestParam(
                                            "unitsData") String unitsData){
        System.out.println("Order placed");
        System.out.println(itemsData+" \n "+quantitiesData+" \n"+unitsData);
        System.out.println("__".repeat(5));
        System.out.println(quickOrderCommand);
        return "redirect:/admin/index";
    }
}