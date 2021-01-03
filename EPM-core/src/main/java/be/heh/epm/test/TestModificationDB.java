package be.heh.epm.test;

import be.heh.epm.application.Context;
import be.heh.epm.application.service.AddHourlyEmployee;
import be.heh.epm.application.service.AddSalariedEmployee;
import be.heh.epm.application.service.AddSalesReceipt;
import be.heh.epm.application.service.AddTimeCard;
import be.heh.epm.application.service.modification.*;
import be.heh.epm.database.DataBaseHelper;
import be.heh.epm.domain.classification.CommissionClassification;
import be.heh.epm.domain.classification.HourlyClassification;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.classification.SalariedClassification;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.domain.payDay.PayCheck;
import be.heh.epm.domain.payMethod.DirectDepositMethod;
import be.heh.epm.domain.payMethod.MailMethod;
import be.heh.epm.domain.payMethod.PaymentMethod;
import be.heh.epm.domain.schedule.MonthlyPaymentSchedule;
import be.heh.epm.domain.schedule.PaymentSchedule;
import be.heh.epm.domain.schedule.TwoWeeksPayementSchedule;
import be.heh.epm.domain.schedule.WeeklyPaymentSchedule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class TestModificationDB {
    @Before
    public void setUp() throws Exception {
        Context.emp = new DataBaseHelper();
    }

    @Test
    public void TestChangeName() {
        int empID = 2;

        ModificationName mn = new ModificationName(empID, "Geoffrey");
        mn.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        Assert.assertEquals("Geoffrey", e.getName());
    }

    @Test
    public void TestChangeMail() {
        int empID = 2;

        ModificationMail mn = new ModificationMail (empID, "geoffrey@heh.be");
        mn.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        Assert.assertEquals("geoffrey@heh.be", e.getMail());
    }

    @Test
    public void TestChangeAddress() {
        int empID = 2;

        ModificationAddress mn = new ModificationAddress (empID, "Havre");
        mn.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        Assert.assertEquals("Havre", e.getAddress());
    }

    @Test
    public void TestChangeHourly() {
        int empID = 3;

        ModificationHourly mn = new ModificationHourly (empID, 20);
        mn.execute();

        LocalDate date = LocalDate.of(2020, 11, 23);
        AddTimeCard tc = new AddTimeCard(empID, date, 8);
        tc.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPayClassification();
        Assert.assertTrue(pc instanceof HourlyClassification);
        PaymentSchedule ps = e.getPaySchedule();
        Assert.assertTrue(ps instanceof WeeklyPaymentSchedule);
    }

    @Test
    public void TestChangeCommissionned() {
        int empID = 10;

        ModificationCommissioned mn = new ModificationCommissioned (empID, 250, 20);
        mn.execute();

        LocalDate date = LocalDate.of(2020, 11, 23);
        AddSalesReceipt rc = new AddSalesReceipt(empID, date, 1000);
        rc.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPayClassification();
        Assert.assertTrue(pc instanceof CommissionClassification);
        PaymentSchedule ps = e.getPaySchedule();
        Assert.assertTrue(ps instanceof TwoWeeksPayementSchedule);
    }

    @Test
    public void TestChangeSalaried() {
        int empID = 11;

        ModificationSalaried mn = new ModificationSalaried (empID, 1250.0);
        mn.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPayClassification();
        Assert.assertTrue(pc instanceof SalariedClassification);
        PaymentSchedule ps = e.getPaySchedule();
        Assert.assertTrue(ps instanceof MonthlyPaymentSchedule);
    }

    @Test
    public void TestChangePayMethod() {
        int empID = 2;

        ModificationDirectDeposit mn = new ModificationDirectDeposit(empID, "Belfius", "BE14 3204 5874 5231");
        mn.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        PaymentMethod pc = e.getPayMethod();
        Assert.assertTrue(pc instanceof DirectDepositMethod);
        Assert.assertEquals("direct deposit into Belfius : BE14 3204 5874 5231", pc.toString());

        empID = 3;

        ModificationMailMethod mmm = new ModificationMailMethod(empID);
        mmm.execute();

        e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        PaymentMethod pc2 = e.getPayMethod();
        Assert.assertTrue(pc2 instanceof MailMethod);
        Assert.assertEquals("mail : Sev@heh.be", pc2.toString());

        empID = 10;

        mn = new ModificationDirectDeposit(empID, "Belfius", "BE14 3204 5874 5231");
        mn.execute();

        e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        pc = e.getPayMethod();
        Assert.assertTrue(pc instanceof DirectDepositMethod);
        Assert.assertEquals("direct deposit into Belfius : BE14 3204 5874 5231", pc.toString());

        empID = 11;

        mmm = new ModificationMailMethod(empID);
        mmm.execute();

        e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        pc2 = e.getPayMethod();
        Assert.assertTrue(pc2 instanceof MailMethod);
        Assert.assertEquals("mail : Trica", pc2.toString());
    }
}
