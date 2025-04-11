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


/**
 * Controller responsible for handling authentication-related requests.
 */
@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    /**
     * Displays the login page.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the login page.
     */
    @GetMapping("/login")
    public String login(Model model) {
        return "auth/login";
    }
}
