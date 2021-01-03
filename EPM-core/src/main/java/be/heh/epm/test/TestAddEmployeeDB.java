package be.heh.epm.test;

import be.heh.epm.application.Context;
import be.heh.epm.application.service.AddCommissionedEmployee;
import be.heh.epm.application.service.AddHourlyEmployee;
import be.heh.epm.application.service.AddSalariedEmployee;
import be.heh.epm.database.DataBaseHelper;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAddEmployeeDB {

// ATTRIBUTES

// CONSTRUCTOR

// GETTERS & SETTERS

// METHODS

    @Before
    public void setUp() throws Exception {
        Context.emp = new DataBaseHelper();
    }

    @Test
    public void testAddSalariedEmployee() {
        AddCommissionedEmployee t = new AddCommissionedEmployee(1, "Gianni", "Charleroi", "Trica", 500.0, 20);
        t.execute();
    }
}
