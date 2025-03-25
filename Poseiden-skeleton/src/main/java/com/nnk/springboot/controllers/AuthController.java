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
     * Displays the registration page.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the registration page.
     */
    @GetMapping("/register")
    public String register(Model model) {
        RegisterDTO dto = new RegisterDTO();
        model.addAttribute("registerDTO", dto);

        return "auth/register";
    }

    /**
     * Handles user registration.
     *
     * @param registerDTO The DTO containing the registration form data.
     * @param result BindingResult to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return A redirection to the login page if successful, otherwise redirects back to the registration page.
     */
    @PostMapping("/register")
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
