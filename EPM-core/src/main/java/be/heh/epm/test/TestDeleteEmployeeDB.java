package be.heh.epm.test;

import be.heh.epm.application.Context;
import be.heh.epm.application.service.*;
import be.heh.epm.database.DataBaseHelper;
import be.heh.epm.domain.employee.Employee;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class TestDeleteEmployeeDB {

// ATTRIBUTES

// CONSTRUCTOR

// GETTERS & SETTERS

// METHODS

    @Before
    public void setUp() throws Exception {
        Context.emp = new DataBaseHelper();
    }

    @Test
    public void testDeleteSalariedEmployee() {
        AddSalariedEmployee t = new AddSalariedEmployee(52, "Adrien", "Home", "Adri@heh.be", 1000.0);
        t.execute();

        getAllEmployees e = new getAllEmployees();
        e.execute();
        TreeMap<Integer, Employee> employeeTreeMap = e.getEmployeeMap();
        int lastKey = employeeTreeMap.lastKey();

        DeleteEmploye d = new DeleteEmploye(lastKey);
        d.execute();
    }
}
