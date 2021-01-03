package be.heh.epm.test;

import be.heh.epm.application.service.AddSalesReceipt;
import be.heh.epm.application.service.AddTimeCard;
import be.heh.epm.domain.classification.*;
import be.heh.epm.application.service.AddHourlyEmployee;
import be.heh.epm.application.service.AddSalariedEmployee;
import be.heh.epm.application.Context;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.application.service.modification.*;
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

public class TestModification {
    @Before
    public void setUp() throws Exception {
        TestContext.setContext();
    }

    @Test
    public void TestChangeName() {
        int empID = 1;
        AddSalariedEmployee a = new AddSalariedEmployee(empID, "Geo", "Mons", "geo@heh.be", 1000.0);
        a.execute();

        ModificationName mn = new ModificationName(empID, "Geoffrey");
        mn.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        Assert.assertEquals("Geoffrey", e.getName());
    }

    @Test
    public void TestChangeMail() {
        int empID = 2;
        AddSalariedEmployee a = new AddSalariedEmployee(empID, "Geo", "Mons", "geo@heh.be", 1000.0);
        a.execute();

        ModificationMail mn = new ModificationMail (empID, "geoffrey@heh.be");
        mn.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        Assert.assertEquals("geoffrey@heh.be", e.getMail());
    }

    @Test
    public void TestChangeAddress() {
        int empID = 3;
        AddSalariedEmployee a = new AddSalariedEmployee(empID, "Geo", "Mons", "geo@heh.be", 1000.0);
        a.execute();

        ModificationAddress mn = new ModificationAddress (empID, "Havré");
        mn.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        Assert.assertEquals("Havré", e.getAddress());
    }

    @Test
    public void TestChangeHourly() {
        int empID = 4;
        AddSalariedEmployee a = new AddSalariedEmployee(empID, "Geo", "Mons", "geo@heh.be", 1000.0);
        a.execute();

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
        LocalDate PayDay = LocalDate.of(2020, 11, 27);
        PayCheck PayC = new PayCheck(PayDay);
        e.payDay(PayC);
        double pay = PayC.getSalary();
        Assert.assertEquals(160.0, pay, 0.01);
    }

    @Test
    public void TestChangeCommissionned() {
        int empID = 5;
        AddSalariedEmployee a = new AddSalariedEmployee(empID, "Geo", "Mons", "geo@heh.be", 1000.0);
        a.execute();

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
        LocalDate PayDay = LocalDate.of(2020, 11, 27);
        PayCheck PayC = new PayCheck(PayDay);
        e.payDay(PayC);
        double pay = PayC.getSalary();
        Assert.assertEquals(450, pay, 0.01);
    }

    @Test
    public void TestChangeSalaried() {
        int empID = 6;
        AddHourlyEmployee a = new AddHourlyEmployee(empID, "Geo", "Mons", "geo@heh.be", 20.0);
        a.execute();

        ModificationSalaried mn = new ModificationSalaried (empID, 1250.0);
        mn.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        PaymentClassification pc = e.getPayClassification();
        Assert.assertTrue(pc instanceof SalariedClassification);
        PaymentSchedule ps = e.getPaySchedule();
        Assert.assertTrue(ps instanceof MonthlyPaymentSchedule);
        LocalDate PayDay = LocalDate.of(2020, 11, 30);
        PayCheck PayC = new PayCheck(PayDay);
        e.payDay(PayC);
        double pay = PayC.getSalary();
        Assert.assertEquals(1250.0, pay, 0.01);
    }

    @Test
    public void TestChangePayMethod() {
        int empID = 7;
        AddSalariedEmployee a = new AddSalariedEmployee(empID, "Geo", "Mons", "geo@heh.be", 1000.0);
        a.execute();

        ModificationDirectDeposit mn = new ModificationDirectDeposit(empID, "Belfius", "BE14 3204 5874 5231");
        mn.execute();

        Employee e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        PaymentMethod pc = e.getPayMethod();
        Assert.assertTrue(pc instanceof DirectDepositMethod);
        Assert.assertEquals("direct deposit into Belfius : BE14 3204 5874 5231", pc.toString());

        ModificationMailMethod mmm = new ModificationMailMethod(empID);
        mmm.execute();

        e = Context.emp.getEmployee(empID);
        Assert.assertNotNull(e);
        PaymentMethod pc2 = e.getPayMethod();
        Assert.assertTrue(pc2 instanceof MailMethod);
        Assert.assertEquals("mail : geo@heh.be", pc2.toString());
    }
}
