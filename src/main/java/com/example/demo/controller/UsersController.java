package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserDetailsWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.demo.utils.accountUtils.UserEntityUtils.user;

@Controller
public class UsersController {

    @Autowired
    UserDetailsWrapper userDetailsWrapper;


    @GetMapping("/register")
    public String registerPage(Model model, @ModelAttribute("user") UserEntity user) {
        model.addAttribute("authenticatedUser", user());
        return "/assets/pages/login-page/register";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute("user") UserEntity user, BindingResult bindingResult) {
        if (userDetailsWrapper.findByEmail(user.getEmail())) {
            bindingResult.addError(new FieldError("user", "email", "Данный email занят"));
        }
        if (userDetailsWrapper.findByLogin(user.getLogin())) {
            bindingResult.addError(new FieldError("user", "login", "Данный login уже занят"));
        }
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            bindingResult.addError(new FieldError("user", "confirmPassword", "Пароли не совпадают"));
        }
        if (bindingResult.hasErrors()) {
            return "/assets/pages/login-page/register";
        }
        userDetailsWrapper.saveUser(user);
        return "redirect:/login";
    }
}
