package be.heh.epm.test;

import be.heh.epm.application.Context;
import be.heh.epm.application.service.AddSalesReceipt;
import be.heh.epm.application.service.AddTimeCard;
import be.heh.epm.database.DataBaseHelper;
import be.heh.epm.domain.classification.CommissionClassification;
import be.heh.epm.domain.classification.HourlyClassification;
import be.heh.epm.domain.classification.SaleReceipt;
import be.heh.epm.domain.classification.TimeCard;
import be.heh.epm.domain.employee.Employee;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestAddSaleReceiptDB {

    @Before
    public void setUp() throws Exception {
        Context.emp = new DataBaseHelper();
    }


    @Test
    public void testAddSaleReceiptCard() {
      LocalDate date = LocalDate.of(2020, 10, 23);
        AddSalesReceipt asr = new AddSalesReceipt(11, date, 600);
        asr.execute();

        Employee e = Context.emp.getEmployee(11);
        CommissionClassification pc = (CommissionClassification) e.getPayClassification();
        SaleReceipt sr = pc.geSaleReceipt(date);


        assertEquals(sr.getSaleAmount(), asr.getSaleAmount(), 0.01);
    }
}
