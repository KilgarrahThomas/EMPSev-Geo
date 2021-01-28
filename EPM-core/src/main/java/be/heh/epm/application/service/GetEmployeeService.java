package be.heh.epm.application.service;

import be.heh.epm.application.port.in.GetEmployeeUserCase;
import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.common.UseCase;
import be.heh.epm.common.WebAdapter;
import be.heh.epm.domain.Employee;

@UseCase
@WebAdapter
public class GetEmployeeService implements GetEmployeeUserCase {

// ATTRIBUTES
    private EmployeePort employeePort;

// CONSTRUCTOR

    public GetEmployeeService(EmployeePort employeePort) {
        this.employeePort = employeePort;
    }


// GETTERS & SETTERS

// METHODS

    @Override
    public Employee execute(int empID) {
        return employeePort.getEmployee(empID);
    }
}
