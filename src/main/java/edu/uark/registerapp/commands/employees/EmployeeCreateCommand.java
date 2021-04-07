package edu.uark.registerapp.commands.employees;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.employees.helpers.EmployeeHelper;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class EmployeeCreateCommand implements ResultCommandInterface<Employee> {
	@Override
	public Employee execute() {
		this.validateProperties();
		if (this.isInitialEmployee) {
			this.apiEmployee.setClassification(EmployeeClassification.GENERAL_MANAGER.getClassification());
		}
		final EmployeeEntity employee =	this.employeeRepository.save(new EmployeeEntity(this.apiEmployee));

		this.apiEmployee.setId(employee.getId());
		this.apiEmployee.setPassword(StringUtils.EMPTY);
		this.apiEmployee.setCreatedOn(employee.getCreatedOn());
		this.apiEmployee.setEmployeeId(EmployeeHelper.padEmployeeId(employee.getEmployeeId()));
		return this.apiEmployee;
	}

	private void validateProperties() {
        if (StringUtils.isBlank(this.apiEmployee.getPassword())) {
			throw new UnprocessableEntityException("Password");
		}
		if (StringUtils.isBlank(this.apiEmployee.getFirstName())) {
			throw new UnprocessableEntityException("First Name");
		}
		if (StringUtils.isBlank(this.apiEmployee.getLastName())) {
			throw new UnprocessableEntityException("Last Name");
		}
		if (!this.isInitialEmployee	&& (EmployeeClassification.map(this.apiEmployee.getClassification()) == EmployeeClassification.NOT_DEFINED)) {
			throw new UnprocessableEntityException("Classification");
		}
	}

    private boolean isInitialEmployee;
	public boolean getIsInitialEmployee() {
		return this.isInitialEmployee;
	}
	public EmployeeCreateCommand setIsInitialEmployee(final boolean isInitialEmployee) {
		this.isInitialEmployee = isInitialEmployee;
		return this;
	}
	private Employee apiEmployee;
	public Employee getApiEmployee() {
		return this.apiEmployee;
	}
	public EmployeeCreateCommand setApiEmployee(final Employee apiEmployee) {
		this.apiEmployee = apiEmployee;
		return this;
	}
	@Autowired
	private EmployeeRepository employeeRepository;
	public EmployeeCreateCommand() {
		this.isInitialEmployee = false;
	}
}