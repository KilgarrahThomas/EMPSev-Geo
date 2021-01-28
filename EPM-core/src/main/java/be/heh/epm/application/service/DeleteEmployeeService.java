package be.heh.epm.application.service;

import be.heh.epm.application.port.in.DeleteEmployeeUserCase;
import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.common.UseCase;

@UseCase
public class DeleteEmployeeService implements DeleteEmployeeUserCase {

// ATTRIBUTES
    private EmployeePort employeePort;

// CONSTRUCTOR
    public DeleteEmployeeService(EmployeePort employeePort) {
        this.employeePort = employeePort;
    }

// GETTERS & SETTERS

// METHODS

    @Override
    public void execute(int empID) {
        employeePort.deleteEmployee(empID);
    }
}
