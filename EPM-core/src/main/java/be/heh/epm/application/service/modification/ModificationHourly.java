package be.heh.epm.application.service.modification;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.classification.HourlyClassification;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.schedule.PaymentSchedule;
import be.heh.epm.domain.schedule.WeeklyPaymentSchedule;

public class ModificationHourly extends ModificationClassification{
    private double salary;

    public ModificationHourly(int empId, double salary) {
        super(empId);
        this.salary = salary;
    }

    @Override
    public PaymentClassification getClassification() {
        Logger.LogInfo(this.getClass().getName(), "Classification : Hourly\n"
                + "Salary : " + salary);
        return new HourlyClassification(salary);
    }

    @Override
    public PaymentSchedule getSchedule() {
        Logger.LogInfo(this.getClass().getName(), "Schedule: Weekly");
        return new WeeklyPaymentSchedule();
    }
}
