package edu.uark.registerapp.commands.employees;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class EmployeeQuery implements ResultCommandInterface<Employee> {
    @Override
    public Employee execute() {
        final Optional<EmployeeEntity> employee = this.employeeRepository.findById(this.employeeId);
        if(employee.isPresent()) {
            return new Employee(employee.get());
        } else {
            throw new NotFoundException("Employee");
        }
    }

    private UUID employeeId;
    public EmployeeQuery setEmployeeId(final UUID employeeId) {
        this.employeeId = employeeId;
        return this;
    }
    public UUID getEmployeeId() {
        return this.employeeId;
    }
    @Autowired
    private EmployeeRepository employeeRepository;
}