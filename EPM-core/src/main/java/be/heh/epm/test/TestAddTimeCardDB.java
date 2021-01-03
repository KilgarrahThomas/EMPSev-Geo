package be.heh.epm.test;

import be.heh.epm.application.Context;
import be.heh.epm.application.service.AddHourlyEmployee;
import be.heh.epm.application.service.AddTimeCard;
import be.heh.epm.database.DataBaseHelper;
import be.heh.epm.domain.classification.HourlyClassification;
import be.heh.epm.domain.classification.TimeCard;
import be.heh.epm.domain.employee.Employee;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestAddTimeCardDB {

    @Before
    public void setUp() throws Exception {
        Context.emp = new DataBaseHelper();
    }


    @Test
    public void testAddTimeCard() {
      LocalDate date = LocalDate.of(2020, 10, 23);
        AddTimeCard atc = new AddTimeCard(10, date, 10);
        //atc.execute();

        Employee e = Context.emp.getEmployee(10);
        HourlyClassification pc = (HourlyClassification) e.getPayClassification();
        TimeCard tc = pc.getTimeCard(date);


        assertEquals(tc.getHours(), atc.getHours(), 0.01);
    }
}
