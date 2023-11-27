package com.knkweb.yarnshop.controller;

import com.knkweb.yarnshop.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class UserControllerAuthorization {
    private final UserService userService;

    public UserControllerAuthorization(UserService userService) {
        this.userService = userService;
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
    public String adminstrationAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("admin page");
        model.addAttribute("username",userDetails.getUsername());
        model.addAttribute("topRole",userService.findMaxRole(userDetails));
        System.out.println(userDetails.getUsername());
        return "admin/aboutme";
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
