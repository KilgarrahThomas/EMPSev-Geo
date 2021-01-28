package be.heh.epm.application.port.in;

import be.heh.epm.domain.Employee;

public interface GetEmployeeUserCase {

// DECLARED METHODS
    public Employee execute(int empID);
}
