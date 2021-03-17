package edu.uark.registerapp.controllers;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import edu.uark.registerapp.models.api.EmployeeType;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.commands.employees.EmployeeQuery;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView employeeDetail(@RequestParam final Map<String, String> queryParameters,	final HttpServletRequest request) {
		final boolean activeUserExists = this.activeUserExists();
		if (activeUserExists) {
			final Optional<ActiveUserEntity> activeUser = this.getCurrentUser(request);
			if (!activeUser.isPresent()) {
				return this.buildInvalidSessionResponse();
			} else if (!this.isElevatedUser(activeUser.get())) {
				return this.buildNoPermissionsResponse();
			}
		}
		return this.buildStartResponse(!activeUserExists, queryParameters);
	}
	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	public ModelAndView startWithEmployee(@PathVariable final UUID employeeId, @RequestParam final Map<String, String> queryParameters,	final HttpServletRequest request) {
		final Optional<ActiveUserEntity> activeUser = this.getCurrentUser(request);
		if (!activeUser.isPresent()) {
			return this.buildInvalidSessionResponse();
		} else if (!this.isElevatedUser(activeUser.get())) {
			return this.buildNoPermissionsResponse();
		}
		return this.buildStartResponse(employeeId, queryParameters);
	}

    
	private boolean activeUserExists() {
		try {
			this.activeEmployeeExistsQuery.execute();
			return true;
		} catch (final NotFoundException e) {
			return false;
		}
	}
	private ModelAndView buildStartResponse(final boolean isInitialEmployee, final Map<String, String> queryParameters) {
		return this.buildStartResponse(isInitialEmployee, (new UUID(0, 0)), queryParameters);
	}
	private ModelAndView buildStartResponse(final UUID employeeId, final Map<String, String> queryParameters) {
		return this.buildStartResponse(false, employeeId, queryParameters);
	}
	private ModelAndView buildStartResponse(final boolean isInitialEmployee, final UUID employeeId, final Map<String, String> queryParameters) {
		ModelAndView modelAndView = this.setErrorMessageFromQueryString(new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()), queryParameters);
		if (employeeId.equals(new UUID(0, 0))) {
			modelAndView.addObject(ViewModelNames.EMPLOYEE.getValue(), (new Employee()).setIsInitialEmployee(isInitialEmployee));
		} else {
			try {
				modelAndView.addObject(ViewModelNames.EMPLOYEE.getValue(), this.employeeQuery.setEmployeeId(employeeId).execute().setIsInitialEmployee(isInitialEmployee));
			} catch (final Exception e) {
				modelAndView.addObject(ViewModelNames.ERROR_MESSAGE.getValue(), e.getMessage());
				modelAndView.addObject(ViewModelNames.EMPLOYEE.getValue(), (new Employee()).setIsInitialEmployee(isInitialEmployee));
			}
		}
		modelAndView.addObject(ViewModelNames.EMPLOYEE_TYPES.getValue(), EmployeeType.allEmployeeTypes());
		return modelAndView;
	}

    
	@Autowired
	private EmployeeQuery employeeQuery;
	@Autowired
	private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
}