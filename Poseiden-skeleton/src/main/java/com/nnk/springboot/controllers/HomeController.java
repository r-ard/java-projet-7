package com.nnk.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller responsible for handling the home page and admin redirection.
 */
@Controller
public class HomeController
{
	/**
	 * Displays the home page.
	 *
	 * @param model The model to store attributes for the view.
	 * @return The view name for the home page.
	 */
	@RequestMapping("/")
	public String home(Model model)
	{
		return "home";
	}
}
