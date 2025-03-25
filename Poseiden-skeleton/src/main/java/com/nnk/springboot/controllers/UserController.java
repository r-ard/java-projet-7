package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDTO;
import com.nnk.springboot.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling user-related requests such as listing,
 * adding, updating, and deleting users.
 */
@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * Displays the list of users.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the user list page.
     */
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Displays the form to add a new user.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the add user page.
     */
    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new UserDTO());

        return "user/add";
    }

    /**
     * Validates and creates a new user.
     *
     * @param userDTO The DTO containing the user data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the user list page if successful, or returns to the add user page if there are errors.
     */
    @PostMapping("/user/validate")
    public String validate(@Valid @ModelAttribute("userDTO") UserDTO userDTO, BindingResult result, Model model) {
        model.addAttribute("user", userDTO);

        if(result.hasErrors()) {
            logger.warn("Failed to create user, invalid user body");
            return "user/add";
        }

        try {
            userService.createUser(userDTO);
        }
        catch(Exception ex) {
            logger.warn("Failed to create user, reason : {}", ex.getMessage());
            return "user/add";
        }

        return "redirect:/user/list";
    }

    /**
     * Displays the form to update an existing user.
     *
     * @param id The ID of the user to update.
     * @param model The model to store attributes for the view.
     * @return The view name for the update user page.
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            UserDTO userDTO = userService.findById(id);
            userDTO.setPassword(""); // Reset password field
            model.addAttribute("user", userDTO);
        } catch (Exception ex) {
            logger.warn("Failed to find user with ID: {}", id);
            return "redirect:/user/list";
        }
        return "user/update";
    }

    /**
     * Updates an existing user.
     *
     * @param id The ID of the user to update.
     * @param userDTO The DTO containing the updated user data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the user list page if successful, or returns to the update user page if there are errors.
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute("userDTO") UserDTO userDTO,
                             BindingResult result, Model model) {
        logger.info("Updating user ID: {}", id);
        model.addAttribute("user", userDTO);

        if (result.hasErrors()) {
            logger.warn("Invalid user data");
            return "user/update";
        }

        try {
            userService.updateUser(id, userDTO);
        } catch (Exception ex) {
            logger.error("Failed to update user: {}", ex.getMessage());
            return "user/update";
        }

        return "redirect:/user/list";
    }

    /**
     * Deletes an existing user.
     *
     * @param id The ID of the user to delete.
     * @return Redirects to the user list page after deletion.
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        logger.info("Deleting user ID: {}", id);

        try {
            userService.delete(id);
        } catch (Exception ex) {
            logger.error("Failed to delete user: {}", ex.getMessage());
        }

        return "redirect:/user/list";
    }
}
