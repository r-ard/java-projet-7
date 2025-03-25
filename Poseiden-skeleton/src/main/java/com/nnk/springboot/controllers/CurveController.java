package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.dto.CurvePointDTO;
import com.nnk.springboot.services.CurvePointService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling curve point-related requests.
 * This includes listing, adding, updating, and deleting curve points.
 */
@Controller
public class CurveController {
    private static final Logger logger = LoggerFactory.getLogger(CurveController.class);

    @Autowired
    private CurvePointService curvePointService;

    /**
     * Displays the list of curve points.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the curve point list page.
     */
    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curvePointService.findAll());
        return "curvePoint/list";
    }

    /**
     * Displays the form to add a new curve point.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the add curve point page.
     */
    @GetMapping("/curvePoint/add")
    public String addCurveForm(Model model) {
        model.addAttribute("curvePoint", new CurvePointDTO());
        return "curvePoint/add";
    }

    /**
     * Validates and saves a new curve point.
     *
     * @param curvePoint The DTO containing the curve point data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the list page if successful, or returns to the add page if there are errors.
     */
    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePointDTO curvePoint, BindingResult result, Model model) {
        logger.info("Creating curvePoint");
        model.addAttribute("curvePoint", curvePoint);

        if (result.hasErrors()) {
            logger.warn("Invalid curvePoint data");
            return "curvePoint/add";
        }

        try {
            curvePointService.save(curvePoint);
        } catch (Exception ex) {
            logger.error("Failed to save curvePoint: {}", ex.getMessage());
            return "curvePoint/add";
        }

        return "redirect:/curvePoint/list";
    }

    /**
     * Displays the form to update an existing curve point.
     *
     * @param id The ID of the curve point to update.
     * @param model The model to store attributes for the view.
     * @return The view name for the update curve point page.
     */
    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("curvePoint", curvePointService.findById(id));
        } catch (Exception ex) {
            logger.warn("CurvePoint not found with ID: {}", id);
            return "redirect:/curvePoint/list";
        }
        return "curvePoint/update";
    }

    /**
     * Updates an existing curve point.
     *
     * @param id The ID of the curve point to update.
     * @param curvePoint The DTO containing the updated curve point data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the list page if successful, or returns to the update page if there are errors or failures.
     */
    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid @ModelAttribute("curvePoint") CurvePointDTO curvePoint, BindingResult result, Model model) {
        logger.info("Updating curvePoint ID: {}", id);
        model.addAttribute("curvePoint", curvePoint);

        if (result.hasErrors()) {
            logger.warn("Invalid curvePoint data");
            return "curvePoint/update";
        }

        try {
            curvePoint.setId(id);
            curvePointService.update(id, curvePoint);
        } catch (Exception ex) {
            logger.error("Failed to update curvePoint: {}", ex.getMessage());
            return "curvePoint/update";
        }

        return "redirect:/curvePoint/list";
    }

    /**
     * Deletes an existing curve point.
     *
     * @param id The ID of the curve point to delete.
     * @return Redirects to the curve point list page after deletion.
     */
    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id) {
        logger.info("Deleting curvePoint ID: {}", id);

        try {
            curvePointService.delete(id);
        } catch (Exception ex) {
            logger.error("Failed to delete curvePoint: {}", ex.getMessage());
        }

        return "redirect:/curvePoint/list";
    }
}
