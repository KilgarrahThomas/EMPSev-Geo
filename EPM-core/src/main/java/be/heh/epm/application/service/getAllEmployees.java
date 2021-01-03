package be.heh.epm.application.service;

import be.heh.epm.application.Context;
import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.employee.Employee;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class getAllEmployees implements Command {

    // ATTRIBUTES
    TreeMap<Integer, Employee> employeeMap = new TreeMap<>();

    // CONSTRUCTORS
    public getAllEmployees() {
        Logger.LogInfo(this.getClass().getName(), "asking for all employees : ");
    }

    // GETTERS & SETTERS

    public TreeMap<Integer, Employee> getEmployeeMap() {
        return employeeMap;
    }

    // METHODS
    @Override
    // Récupère tous les employés
    public void execute() {
        employeeMap = (TreeMap<Integer, Employee>) Context.emp.getAllEmployees();
    }
}
