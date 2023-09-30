package com.knkweb.yarnshop.controller;

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
    public String welcomeAdmin(Model model){
        System.out.println("admin welcome");
        return "admin/welcome";
    }
    @RequestMapping("/admin/aboutme")
    public String aboutMeAdmin(Model model){
        System.out.println("admin aboutme");
        return "admin/aboutme";
    }

    @RequestMapping("/customer/index")
    public String welcomeCustomer(Model model){
        System.out.println("welcome customer");
        return "customer/welcome";
    }

    @RequestMapping("/customer/aboutme")
    public String aboutMeCustomer(Model model){
        System.out.println("customer aboutme");
        return "customer/aboutme";
    }
}
