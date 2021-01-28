package be.heh.epm.domain;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TestEmployee {

    private Employee employee;
    private PayCheck pc;

    @Before
    public void setUp() throws Exception {
        employee = new Employee(100, "toto", "av maistriau", "toto@gmail.com");
        LocalDate payDate = LocalDate.of(2019, 10, 2);
        pc = new PayCheck(payDate);
    }

    @Test
    public void createSalariedEmployee() {

        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new MonthlyPaymentSchedule());

        employee.payDay(pc);
        double pay = pc.getSalary();
        assertEquals(1000.0, pay, 0.01);

        PaymentSchedule ps = employee.getPaySchedule();
        assertTrue(ps instanceof MonthlyPaymentSchedule);

        PaymentMethod pm = employee.getPayMethod();
        //assertEquals("direct deposit into ING : be80-4444-444", pm.toString());

    }

    @Ignore
    @Test
    public void createHourlyEmployee() {

        employee.setPayClassification(new HourlyClassification(20.0));
        employee.setPayMethod(new MailMethod(employee.getMail()));
        employee.setPaySchedule(new WeeklyPaymentSchedule());

        LocalDate date = LocalDate.of(2019, 10, 1);
        LocalDate nextDate = LocalDate.of(2019, 10, 2);
        LocalDate dateOutside = LocalDate.of(2019, 9, 2);

        PaymentClassification classification = employee.getPayClassification();
        ((HourlyClassification) classification).addTimeCard(new TimeCard(date, 8.0));
        ((HourlyClassification) classification).addTimeCard(new TimeCard(nextDate, 10.0));
        ((HourlyClassification) classification).addTimeCard(new TimeCard(dateOutside, 8.0));

        employee.payDay(pc);
        double pay = pc.getSalary();

        assertEquals(380.0, pay, 0.01);

        PaymentSchedule ps = employee.getPaySchedule();
        assertTrue(ps instanceof WeeklyPaymentSchedule);

        PaymentMethod pm = employee.getPayMethod();
        assertEquals("mail : toto@gmail.com", pm.toString());

    }

    @Ignore
    @Test
    public void monthlyPaymentSchedule() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new MonthlyPaymentSchedule());

        LocalDate LastDayOfMonth = LocalDate.of(2019, 10, 31);

        assertTrue(employee.isDatePay(LastDayOfMonth));

    }
    @Ignore
    @Test
    public void monthlyPaymentScheduleWrong() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new MonthlyPaymentSchedule());

        LocalDate firstDayOfMonthWrong = LocalDate.of(2019, 10, 1);

        assertFalse(employee.isDatePay(firstDayOfMonthWrong));

    }

    @Ignore
    @Test
    public void weeklyPaymentSchedule() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new WeeklyPaymentSchedule());

        LocalDate fridayDate = LocalDate.of(2020, 10, 2);

        assertTrue(employee.isDatePay(fridayDate));

    }

    @Ignore
    @Test
    public void weeklyPaymentScheduleWrong() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new WeeklyPaymentSchedule());

        LocalDate MondayDate = LocalDate.of(2020, 10, 5);

        assertFalse(employee.isDatePay(MondayDate));

    }
}
