package be.heh.epm.application.service.modification;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.domain.schedule.PaymentSchedule;

public abstract class ModificationClassification extends ModificationEmployee{

    public ModificationClassification(int empId) {
        super(empId);
    }

    public abstract PaymentClassification getClassification();

    public abstract PaymentSchedule getSchedule();

    @Override
    public void modification(Employee employee) {
        Logger.LogInfo(this.getClass().getName(), "Modifying Classification and Schedule for " + employee.getName());
        employee.setPayClassification(getClassification());
        employee.setPaySchedule(getSchedule());
    }
}
