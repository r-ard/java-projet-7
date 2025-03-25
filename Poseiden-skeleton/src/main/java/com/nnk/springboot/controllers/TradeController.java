package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.dto.TradeDTO;
import com.nnk.springboot.services.TradeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling trade related requests.
 * This includes listing, adding, updating, and deleting trades.
 */
@Controller
public class TradeController {
    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;

    /**
     * Displays the list of trades.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the trade list page.
     */
    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.findAll());
        return "trade/list";
    }

    /**
     * Displays the form to add a new trade.
     *
     * @param model The model to store attributes for the view.
     * @return The view name for the add trade page.
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
        model.addAttribute("trade", new TradeDTO());
        return "trade/add";
    }

    /**
     * Validates and saves a new trade.
     *
     * @param trade The DTO containing the trade data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the list page if successful, or returns to the add page if there are errors.
     */
    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute("trade") TradeDTO trade, BindingResult result, Model model) {
        logger.info("Creating trade");
        model.addAttribute("trade", trade);

        if (result.hasErrors()) {
            logger.warn("Invalid trade data");
            return "trade/add";
        }

        try {
            tradeService.save(trade);
        } catch (Exception ex) {
            logger.error("Failed to save trade: {}", ex.getMessage());
            return "trade/add";
        }

        return "redirect:/trade/list";
    }

    /**
     * Displays the form to update an existing trade.
     *
     * @param id The ID of the trade to update.
     * @param model The model to store attributes for the view.
     * @return The view name for the update trade page.
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        try {
            model.addAttribute("trade", tradeService.findById(id));
        } catch (Exception ex) {
            logger.warn("Trade not found with ID: {}", id);
            return "redirect:/trade/list";
        }
        return "trade/update";
    }

    /**
     * Updates an existing trade.
     *
     * @param id The ID of the trade to update.
     * @param trade The DTO containing the updated trade data.
     * @param result The binding result to handle validation errors.
     * @param model The model to store attributes for the view.
     * @return Redirects to the list page if successful, or returns to the update page if there are errors or failures.
     */
    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid @ModelAttribute("trade") TradeDTO trade, BindingResult result, Model model) {
        logger.info("Updating trade ID: {}", id);
        model.addAttribute("trade", trade);

        if (result.hasErrors()) {
            logger.warn("Invalid trade data");
            return "trade/update";
        }

        try {
            trade.setId(id);
            tradeService.update(id, trade);
        } catch (Exception ex) {
            logger.error("Failed to update trade: {}", ex.getMessage());
            return "trade/update";
        }

        return "redirect:/trade/list";
    }

    /**
     * Deletes an existing trade.
     *
     * @param id The ID of the trade to delete.
     * @return Redirects to the trade list page after deletion.
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id) {
        logger.info("Deleting trade ID: {}", id);

        try {
            tradeService.delete(id);
        } catch (Exception ex) {
            logger.error("Failed to delete trade: {}", ex.getMessage());
        }

        return "redirect:/trade/list";
    }
}
