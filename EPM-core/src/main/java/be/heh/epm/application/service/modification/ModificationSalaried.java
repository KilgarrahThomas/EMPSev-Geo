package be.heh.epm.application.service.modification;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.classification.SalariedClassification;
import be.heh.epm.domain.schedule.MonthlyPaymentSchedule;
import be.heh.epm.domain.schedule.PaymentSchedule;

public class ModificationSalaried extends ModificationClassification{
    private double salary;

    public ModificationSalaried(int empId, double salary) {
        super(empId);
        this.salary = salary;
    }

    @Override
    public PaymentClassification getClassification() {
        Logger.LogInfo(this.getClass().getName(), "Classification : Salaried\n"
                + "Salary : " + salary);
        return new SalariedClassification(salary);
    }

    @Override
    public PaymentSchedule getSchedule() {
        Logger.LogInfo(this.getClass().getName(), "Schedule: Monthly");
        return new MonthlyPaymentSchedule();
    }
}
