package edu.uark.registerapp.commands.employees;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import edu.uark.registerapp.commands.VoidCommandInterface;

import edu.uark.registerapp.models.repositories.EmployeeRepository;
import edu.uark.registerapp.commands.exceptions.NotFoundException;

@Service
public class ActiveEmployeeExistsQuery implements VoidCommandInterface
{
    @Override
    public void execute() {
        if(!this.employeeRepository.existsByIsActive(true)) {
            throw new NotFoundException("Employee");
        }
    }
    @Autowired
    private EmployeeRepository employeeRepository;
}