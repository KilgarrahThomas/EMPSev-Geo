package be.heh.epm.test;

import be.heh.epm.application.Context;
import be.heh.epm.application.service.*;
import be.heh.epm.application.service.modification.ModificationDirectDeposit;
import be.heh.epm.database.DataBaseHelper;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.domain.payDay.PayCheck;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

public class TestPayDayDB {
    @Before
    public void setUp() throws Exception {
        Context.emp = new DataBaseHelper();
    }

    @Test
    public void testPayDay() {
        LocalDate datePayDay = LocalDate.of(2020, 11, 30);
        PayDay payDay = new PayDay(datePayDay);
        payDay.execute();

        Map<Employee, PayCheck> PCL = Context.emp.getPayCheckForDate(datePayDay);
        System.out.println("Transfert for " + datePayDay.toString());
        System.out.println("Name\tSalary\tMethod");
        for (Map.Entry<Employee, PayCheck> entry : PCL.entrySet())  {
            Employee e = entry.getKey();
            PayCheck pc = entry.getValue();
            System.out.println(e.getName() +"\t" + pc.getSalary() +"\t" + pc.getMethod());
        };
    }
}
