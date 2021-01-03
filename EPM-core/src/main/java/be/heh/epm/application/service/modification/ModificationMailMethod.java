package be.heh.epm.application.service.modification;

import be.heh.epm.application.Context;
import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.payMethod.MailMethod;
import be.heh.epm.domain.payMethod.PaymentMethod;

public class ModificationMailMethod extends ModificationPayMethod {
    // ATTRIBUTES
    private String mail;

    public ModificationMailMethod(int empId) {
        super(empId);
        mail = Context.emp.getEmployee(empId).getMail();
    }

    @Override
    public PaymentMethod getMethod() {
        Logger.LogInfo(this.getClass().getName(), "PayMethod: mail to " + mail);
        return new MailMethod(mail);
    }
}
