package edu.uark.registerapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.products.ProductsQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;

@Controller
@RequestMapping(value = "/mainMenu")
public class MainMenuRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showProductListing() {
		ModelAndView modelAndView =
			new ModelAndView(ViewNames.MAIN_MENU.getViewName());

		try {
			modelAndView.addObject(
				ViewModelNames.PRODUCTS.getValue(),
				this.productsQuery.execute());
		} catch (final Exception e) {
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
				e.getMessage());
			modelAndView.addObject(
				ViewModelNames.PRODUCTS.getValue(),
				(new Product[0]));
		}
		
		return modelAndView;
	}

	// Properties
	@Autowired
	private ProductsQuery productsQuery;
}
