package be.heh.epm.application.service;

import be.heh.epm.application.port.in.GetAllEmployeesUserCase;
import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.common.UseCase;
import be.heh.epm.common.WebAdapter;
import be.heh.epm.domain.Employee;

import java.util.ArrayList;

@UseCase
@WebAdapter
public class GetAllEmployeesService implements GetAllEmployeesUserCase {

// ATTRIBUTES
    private EmployeePort employeePort;

// CONSTRUCTOR

    public GetAllEmployeesService(EmployeePort employeePort) {
        this.employeePort = employeePort;
    }

// GETTERS & SETTERS

// METHODS

    @Override
    public ArrayList<Employee> execute() {

        return employeePort.getAllEmployee();
    }
}
