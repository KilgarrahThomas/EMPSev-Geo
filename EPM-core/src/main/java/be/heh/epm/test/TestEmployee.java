package be.heh.epm.test;

import be.heh.epm.domain.classification.HourlyClassification;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.classification.SalariedClassification;
import be.heh.epm.domain.classification.TimeCard;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.domain.payMethod.DirectDepositMethod;
import be.heh.epm.domain.payMethod.MailMethod;
import be.heh.epm.domain.payMethod.PaymentMethod;
import be.heh.epm.domain.schedule.MonthlyPaymentSchedule;
import be.heh.epm.domain.schedule.PaymentSchedule;
import be.heh.epm.domain.schedule.TwoWeeksPayementSchedule;
import be.heh.epm.domain.schedule.WeeklyPaymentSchedule;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TestEmployee {

    private Employee employee;
    private be.heh.epm.domain.payDay.PayCheck pc;

    @Before
    public void setUp() throws Exception {
        employee = new Employee(100, "toto", "av maistriau", "toto@gmail.com");
        LocalDate payDate = LocalDate.of(2020, 10, 30);
        pc = new be.heh.epm.domain.payDay.PayCheck(payDate);
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
        assertEquals("direct deposit into ING : be80-4444-444", pm.toString());

    }

    @Test
    public void createHourlyEmployee() {

        employee.setPayClassification(new HourlyClassification(20.0));
        employee.setPayMethod(new MailMethod(employee.getMail()));
        employee.setPaySchedule(new WeeklyPaymentSchedule());

        LocalDate date = LocalDate.of(2020, 10, 29);
        LocalDate nextDate = LocalDate.of(2020, 10, 30);
        LocalDate dateOutside = LocalDate.of(2020, 9, 2);

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

    @Test
    public void monthlyPaymentSchedule() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new MonthlyPaymentSchedule());

        LocalDate LastDayOfMonth = LocalDate.of(2019, 10, 31);

        assertTrue(employee.isDatePay(LastDayOfMonth));

    }

    @Test
    public void monthlyPaymentScheduleNotLastDayTrue() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new MonthlyPaymentSchedule());

        LocalDate LastDayOfMonth = LocalDate.of(2020, 10, 30);

        assertTrue(employee.isDatePay(LastDayOfMonth));

    }

    @Test
    public void monthlyPaymentScheduleLastDayWrong() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new MonthlyPaymentSchedule());

        LocalDate LastDayOfMonth = LocalDate.of(2020, 10, 31);

        assertFalse(employee.isDatePay(LastDayOfMonth));

    }

    @Test
    public void monthlyPaymentScheduleWrong() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new MonthlyPaymentSchedule());

        LocalDate firstDayOfMonthWrong = LocalDate.of(2019, 10, 1);

        assertFalse(employee.isDatePay(firstDayOfMonthWrong));

    }

    @Test
    public void weeklyPaymentSchedule() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new WeeklyPaymentSchedule());

        LocalDate fridayDate = LocalDate.of(2020, 10, 2);

        assertTrue(employee.isDatePay(fridayDate));

    }

    @Test
    public void weeklyPaymentScheduleWrong() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new WeeklyPaymentSchedule());

        LocalDate MondayDate = LocalDate.of(2020, 10, 5);

        assertFalse(employee.isDatePay(MondayDate));

    }

    @Test
    public void TwoWeeksPaymentSchedule() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new TwoWeeksPayementSchedule());

        LocalDate SecondFriday = LocalDate.of(2020, 10, 9);

        assertTrue(employee.isDatePay(SecondFriday));

    }

    @Test
    public void TwoWeeksPaymentScheduleWrong() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new TwoWeeksPayementSchedule());

        LocalDate FirstFriday = LocalDate.of(2020, 10, 2);

        assertFalse(employee.isDatePay(FirstFriday));

    }

    @Test
    public void TwoWeeksPaymentScheduleMondayWeek4() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new TwoWeeksPayementSchedule());

        LocalDate SecondFriday = LocalDate.of(2020, 10, 23);
        LocalDate Start = LocalDate.of(2020, 10, 12);
        be.heh.epm.domain.payDay.PayCheck pc2 = new be.heh.epm.domain.payDay.PayCheck(SecondFriday);

        employee.payDay(pc2);

        assertEquals(pc2.getPayPayPeriodStart(), Start);

    }

    @Test
    public void TwoWeeksPaymentScheduleMondayWeek2() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new TwoWeeksPayementSchedule());

        LocalDate SecondFriday = LocalDate.of(2020, 10, 9);
        LocalDate Start = LocalDate.of(2020, 9, 28);
        be.heh.epm.domain.payDay.PayCheck pc2 = new be.heh.epm.domain.payDay.PayCheck(SecondFriday);

        employee.payDay(pc2);

        assertEquals(pc2.getPayPayPeriodStart(), Start);

    }

    @Test
    public void ThreeWeeksPaymentScheduleMonday() {
        employee.setPayClassification(new SalariedClassification(1000));
        employee.setPayMethod(new DirectDepositMethod("ING", "be80-4444-444"));
        employee.setPaySchedule(new TwoWeeksPayementSchedule());

        LocalDate SecondFriday = LocalDate.of(2020, 8, 14);
        LocalDate Start = LocalDate.of(2020, 7, 27);
        be.heh.epm.domain.payDay.PayCheck pc2 = new be.heh.epm.domain.payDay.PayCheck(SecondFriday);

        employee.payDay(pc2);

        assertEquals(pc2.getPayPayPeriodStart(), Start);

    }
}
