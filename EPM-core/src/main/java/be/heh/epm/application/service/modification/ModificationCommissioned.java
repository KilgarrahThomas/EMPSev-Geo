package be.heh.epm.application.service.modification;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.classification.CommissionClassification;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.schedule.PaymentSchedule;
import be.heh.epm.domain.schedule.TwoWeeksPayementSchedule;

public class ModificationCommissioned extends ModificationClassification{
    private double salary;
    private double commission;

    public ModificationCommissioned(int empId, double salary, double commission) {
        super(empId);
        this.salary = salary;
        this.commission = commission;
    }

    @Override
    public PaymentClassification getClassification() {
        Logger.LogInfo(this.getClass().getName(), "Classification : Commissionned\n"
        + "Salary : " + salary + "\nCommission : " + commission);
        return new CommissionClassification(salary, commission);
    }

    @Override
    public PaymentSchedule getSchedule() {
        Logger.LogInfo(this.getClass().getName(), "Schedule: Two Weeks");
        return new TwoWeeksPayementSchedule();
    }
}
