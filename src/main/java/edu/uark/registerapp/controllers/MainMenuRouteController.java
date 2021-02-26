package edu.uark.registerapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import edu.uark.registerapp.controllers.enums.ViewNames;

@Controller
@RequestMapping(value = "/")
public class MainMenuRouteController {
	@GetMapping("")
	 public ModelAndView mainMenu() {
		ModelAndView modelAndView =
			new ModelAndView(ViewNames.MAIN_MENU.getViewName());
		
		return modelAndView;
	}
	public String getQuery(@RequestParam String query)
	{
		return query;
	}
}
