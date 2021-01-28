package be.heh.epm.application.port.in;

import be.heh.epm.domain.Employee;

import java.util.ArrayList;

public interface GetAllEmployeesUserCase {

    // DECLARED METHODS
    public ArrayList<Employee> execute();
}
