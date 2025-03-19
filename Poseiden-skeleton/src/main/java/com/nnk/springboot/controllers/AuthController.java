package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.auth.RegisterDTO;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("register")
    public String register(Model model) {
        return "auth/register";
    }

    @PostMapping("register")
    public String register(
            @ModelAttribute("registerDTO") @Valid RegisterDTO registerDTO,
            BindingResult result,
            Model model
    ) {
        if(result.hasErrors()) {

            return "redirect:/register";
        }

        return "redirect:/login";
    }

    @GetMapping("login")
    public String login(Model model) {
        return "auth/login";
    }
}
