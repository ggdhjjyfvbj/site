package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static com.example.demo.utils.accountUtils.UserEntityUtils.user;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("authenticatedUser", user());
        return "/assets/pages/login-page/login";
    }


}
