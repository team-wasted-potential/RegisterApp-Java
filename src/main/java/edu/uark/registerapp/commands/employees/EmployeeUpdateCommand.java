package edu.uark.registerapp.commands.employees;
import java.util.Optional;
import java.util.UUID;
import edu.uark.registerapp.commands.ResultCommandInterface;
import javax.transaction.Transactional;
import edu.uark.registerapp.models.enums.EmployeeClassification;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;

@Service
public class EmployeeUpdateCommand implements ResultCommandInterface<Employee> {
	@Override
	public Employee execute() {
		this.validateProperties();
		this.updateEmployeeEntity();
		return this.apiEmployee;
	}

	private void validateProperties() {
		if (StringUtils.isBlank(this.apiEmployee.getFirstName())) {
			throw new UnprocessableEntityException("First name");
		}
		if (StringUtils.isBlank(this.apiEmployee.getLastName())) {
			throw new UnprocessableEntityException("Last name");
		}
		if (EmployeeClassification.map(this.apiEmployee.getClassification()) == EmployeeClassification.NOT_DEFINED) {
			throw new UnprocessableEntityException("Classification");
		}
	}
	@Transactional
	private void updateEmployeeEntity() {
		final Optional<EmployeeEntity> queriedEmployee = this.employeeRepository.findById(this.employeeId);
		if (!queriedEmployee.isPresent()) {
			throw new NotFoundException("Employee");
		}
		this.apiEmployee = queriedEmployee.get().synchronize(this.apiEmployee);
		this.employeeRepository.save(queriedEmployee.get());
	}

    private Employee apiEmployee;
	public Employee getApiEmployee() {
		return this.apiEmployee;
	}
	private UUID employeeId;
	public UUID getEmployeeId() {
		return this.employeeId;
	}
    public EmployeeUpdateCommand setApiEmployee(final Employee apiEmployee) {
		this.apiEmployee = apiEmployee;
		return this;
	}
	public EmployeeUpdateCommand setEmployeeId(final UUID employeeId) {
		this.employeeId = employeeId;
		return this;
	}
	@Autowired
	private EmployeeRepository employeeRepository;
}