package com.nnk.springboot.controllers;

import com.nnk.springboot.dto.BidListDTO;
import com.nnk.springboot.services.BidService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


/**
 * Controller responsible for handling bid list-related requests.
 * This includes listing, adding, updating, and deleting bid lists.
 */
@Controller
public class BidListController {
    private static final Logger logger = LoggerFactory.getLogger(BidListController.class);

    @Autowired
    private BidService bidService;

    /**
     * Displays the list of bid lists.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the bid list page.
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidLists", bidService.findAll());
        return "bidList/list";
    }

    /**
     * Displays the form to add a new bid list.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the add bid list page.
     */
    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        BidListDTO dto = new BidListDTO();
        model.addAttribute("bidList", dto);

        return "bidList/add";
    }

    /**
     * Validates and saves a new bid list.
     *
     * @param dto The DTO containing the bid list data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return The view name for the add bid list page if errors exist, or redirects to the list page if successful.
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bidList")BidListDTO dto, BindingResult result, Model model) {
        logger.info("Creating bidList");

        model.addAttribute("bidList", dto);

        if(result.hasErrors()) {
            logger.warn("Can not create bidList, invalid bidList body");
            return "bidList/add";
        }

        try {
            bidService.save(dto);
        }
        catch(Exception ex) {
            logger.error("Failed to create bidList in database, reason : {}", ex.getMessage());
            return "bidList/add";
        }

        return "bidList/add";
    }

    /**
     * Displays the form to update an existing bid list.
     *
     * @param id The ID of the bid list to update.
     * @param model The model to store attributes for the view.
     * @return The view name for the update bid list page.
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        BidListDTO dto = null;

        try {
            dto = bidService.findById(id);
        }
        catch(Exception ex) {
            logger.warn("Failed to find bidList in database with ID : {}", id);
            return "bidList/update";
        }

        model.addAttribute("bidList", dto);

        return "bidList/update";
    }

    /**
     * Updates an existing bid list.
     *
     * @param id The ID of the bid list to update.
     * @param bidList The DTO containing the updated bid list data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the bid list page if successful, or returns to the update form if there are errors or failures.
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bidList") BidListDTO bidList,
                             BindingResult result, Model model) {
        logger.info("Updating bidList ID: {}", id);

        model.addAttribute("bidList", bidList);

        if(result.hasErrors()) {
            logger.warn("Can not update bidList, invalid bidList body");
            return "bidList/update";
        }

        BidListDTO existingBidList = null;
        try {
            existingBidList = bidService.findById(id);
        }
        catch(Exception ex) {
            existingBidList = null;
        }

        if(existingBidList == null) {
            logger.warn("Failed to find bidList in database with ID : {}", id);
            return "bidList/update";
        }

        bidList.setId(id);

        try {
            bidService.update(id, bidList);
        }
        catch(Exception ex) {
            logger.error("Failed to update bidList in database, reason : {}", ex.getMessage());
            return "bidList/update";
        }


        return "redirect:/bidList/list";
    }

    /**
     * Deletes an existing bid list.
     *
     * @param id The ID of the bid list to delete.
     * @param model The model to store attributes for the view.
     * @return Redirects to the bid list page after successful deletion or failure.
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("Deleting bidList ID: {}", id);

        try {
            bidService.delete(id);
        }
        catch(Exception ex) {
            logger.error("Failed to delete bidList in database, reason : {}", ex.getMessage());
            return "redirect:/bidList/list";
        }

        return "redirect:/bidList/list";
    }
}
