package be.heh.epm.application.service.modification;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.domain.payMethod.PaymentMethod;

public abstract class ModificationPayMethod extends ModificationEmployee{

    public ModificationPayMethod(int empId) {
        super(empId);
    }

    public abstract PaymentMethod getMethod();

    @Override
    public void modification(Employee employee) {
        Logger.LogInfo(this.getClass().getName(), "Modifying PayMethod for " + employee.getName());
        employee.setPayMethod(getMethod());
    }
}
