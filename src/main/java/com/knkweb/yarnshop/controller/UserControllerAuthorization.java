package com.knkweb.yarnshop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserControllerAuthorization {

    @RequestMapping({"/","/index","/index.html"})
    public String indexPage(Model model){
        return "all/index";
    }

//    http://localhost:8080/admin
//    http://localhost:8080/admin/
//    http://localhost:8080/admin/index
//    http://localhost:8080/admin/index/

    @RequestMapping({"/admin/index","/admin"})
    public String welcomeAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("admin welcome");
        model.addAttribute("username",userDetails.getUsername());
        System.out.println(userDetails.getUsername());
        return "admin/welcome";
    }
    @RequestMapping("/admin/aboutme")
    public String aboutMeAdmin(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("admin aboutme");
        model.addAttribute("username",userDetails.getUsername());
        System.out.println(userDetails.getUsername());
        return "admin/aboutme";
    }

    @RequestMapping("/customer/index")
    public String welcomeCustomer(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("welcome customer");
        model.addAttribute("username",userDetails.getUsername());
        System.out.println(userDetails.getUsername());
        return "customer/welcome";
    }

    @RequestMapping("/customer/aboutme")
    public String aboutMeCustomer(Model model, @AuthenticationPrincipal UserDetails userDetails){
        System.out.println("customer aboutme");
        model.addAttribute("username",userDetails.getUsername());
        System.out.println(userDetails.getUsername());
        return "customer/aboutme";
    }
}
