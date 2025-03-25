package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDTO;
import com.nnk.springboot.services.RuleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling rule name related requests.
 * This includes listing, adding, updating, and deleting rule names.
 */
@Controller
public class RuleNameController {
    private static final Logger logger = LoggerFactory.getLogger(RuleNameController.class);

    @Autowired
    private RuleService ruleService;

    /**
     * Displays the list of rule names.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the rule name list page.
     */
    @RequestMapping("/ruleName/list")
    public String home(Model model) {
        model.addAttribute("ruleNames", ruleService.findAll());
        return "ruleName/list";
    }

    /**
     * Displays the form to add a new rule name.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the add rule name page.
     */
    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleNameDTO());
        return "ruleName/add";
    }

    /**
     * Validates and saves a new rule name.
     *
     * @param ruleName The DTO containing the rule name data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the list page if successful, or returns to the add page if there are errors.
     */
    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleNameDTO ruleName, BindingResult result, Model model) {
        logger.info("Creating ruleName");
        model.addAttribute("ruleName", ruleName);

        if (result.hasErrors()) {
            logger.warn("Invalid ruleName data");
            return "ruleName/add";
        }

        try {
            ruleService.save(ruleName);
        } catch (Exception ex) {
            logger.error("Failed to save ruleName: {}", ex.getMessage());
            return "ruleName/add";
        }

        return "redirect:/ruleName/list";
    }

    /**
     * Displays the form to update an existing rule name.
     *
     * @param id The ID of the rule name to update.
     * @param model The model to store attributes for the view.
     * @return The view name for the update rule name page.
     */
    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("ruleName", ruleService.findById(id));
        } catch (Exception ex) {
            logger.warn("RuleName not found with ID: {}", id);
            return "redirect:/ruleName/list";
        }
        return "ruleName/update";
    }

    /**
     * Updates an existing rule name.
     *
     * @param id The ID of the rule name to update.
     * @param ruleName The DTO containing the updated rule name data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the list page if successful, or returns to the update page if there are errors or failures.
     */
    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid @ModelAttribute("ruleName") RuleNameDTO ruleName, BindingResult result, Model model) {
        logger.info("Updating ruleName ID: {}", id);
        model.addAttribute("ruleName", ruleName);

        if (result.hasErrors()) {
            logger.warn("Invalid ruleName data");
            return "ruleName/update";
        }

        try {
            ruleName.setId(id);
            ruleService.update(id, ruleName);
        } catch (Exception ex) {
            logger.error("Failed to update ruleName: {}", ex.getMessage());
            return "ruleName/update";
        }

        return "redirect:/ruleName/list";
    }

    /**
     * Deletes an existing rule name.
     *
     * @param id The ID of the rule name to delete.
     * @return Redirects to the rule name list page after deletion.
     */
    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id) {
        logger.info("Deleting ruleName ID: {}", id);

        try {
            ruleService.delete(id);
        } catch (Exception ex) {
            logger.error("Failed to delete ruleName: {}", ex.getMessage());
        }

        return "redirect:/ruleName/list";
    }
}
