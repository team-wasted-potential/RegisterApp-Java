package edu.uark.registerapp.controllers;

import org.springframework.stereotype.Controller;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/mainMenu")
public class MainMenuRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView mainMenu(@RequestParam final Map<String, String> queryParameters, final HttpServletRequest request) {

		final Optional<ActiveUserEntity> activeUserEntity = this.getCurrentUser(request);
		if(!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse();
		}
		ModelAndView modelAndView = this.setErrorMessageFromQueryString(new ModelAndView(ViewNames.MAIN_MENU.getViewName()), queryParameters);
		modelAndView.addObject(ViewModelNames.IS_ELEVATED_USER.getValue(), this.isElevatedUser(activeUserEntity.get()));
		return modelAndView;
	}
}