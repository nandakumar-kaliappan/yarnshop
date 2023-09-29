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

    @RequestMapping({"/admin/index"})
    public String welcomeAdmin(Model model){
        return "admin/welcome";
    }
    @RequestMapping({"/admin/aboutme"})
    public String aboutMeAdmin(Model model){
        return "admin/aboutme";
    }

    @RequestMapping({"/customer/index"})
    public String welcomeCustomer(Model model){
        return "Customer/welcome";
    }

    @RequestMapping({"/customer/aboutme"})
    public String aboutMeCustomer(Model model){
        return "Customer/aboutme";
    }
}
