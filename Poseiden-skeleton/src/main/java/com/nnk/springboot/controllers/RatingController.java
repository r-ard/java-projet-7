package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.dto.RatingDTO;
import com.nnk.springboot.services.RatingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling rating-related requests.
 * This includes listing, adding, updating, and deleting ratings.
 */
@Controller
public class RatingController {
    private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

    @Autowired
    private RatingService ratingService;

    /**
     * Displays the list of ratings.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the rating list page.
     */
    @RequestMapping("/rating/list")
    public String home(Model model) {
        model.addAttribute("ratings", ratingService.findAll());
        return "rating/list";
    }

    /**
     * Displays the form to add a new rating.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the add rating page.
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new RatingDTO());
        return "rating/add";
    }

    /**
     * Validates and saves a new rating.
     *
     * @param rating The DTO containing the rating data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the list page if successful, or returns to the add page if there are errors.
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") RatingDTO rating, BindingResult result, Model model) {
        logger.info("Creating rating");
        model.addAttribute("rating", rating);

        if (result.hasErrors()) {
            logger.warn("Invalid rating data");
            return "rating/add";
        }

        try {
            ratingService.save(rating);
        } catch (Exception ex) {
            logger.error("Failed to save rating: {}", ex.getMessage());
            return "rating/add";
        }

        return "redirect:/rating/list";
    }

    /**
     * Displays the form to update an existing rating.
     *
     * @param id The ID of the rating to update.
     * @param model The model to store attributes for the view.
     * @return The view name for the update rating page.
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("rating", ratingService.findById(id));
        } catch (Exception ex) {
            logger.warn("Rating not found with ID: {}", id);
            return "redirect:/rating/list";
        }
        return "rating/update";
    }

    /**
     * Updates an existing rating.
     *
     * @param id The ID of the rating to update.
     * @param rating The DTO containing the updated rating data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the list page if successful, or returns to the update page if there are errors or failures.
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid @ModelAttribute("rating") RatingDTO rating, BindingResult result, Model model) {
        logger.info("Updating rating ID: {}", id);
        model.addAttribute("rating", rating);

        if (result.hasErrors()) {
            logger.warn("Invalid rating data");
            return "rating/update";
        }

        try {
            rating.setId(id);
            ratingService.update(id, rating);
        } catch (Exception ex) {
            logger.error("Failed to update rating: {}", ex.getMessage());
            return "rating/update";
        }

        return "redirect:/rating/list";
    }

    /**
     * Deletes an existing rating.
     *
     * @param id The ID of the rating to delete.
     * @return Redirects to the rating list page after deletion.
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id) {
        logger.info("Deleting rating ID: {}", id);

        try {
            ratingService.delete(id);
        } catch (Exception ex) {
            logger.error("Failed to delete rating: {}", ex.getMessage());
        }

        return "redirect:/rating/list";
    }
}
