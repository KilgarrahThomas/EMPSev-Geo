package be.heh.epm.application.service.modification;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.employee.Employee;

public class ModificationMail extends ModificationEmployee{
    private String mail;

    public ModificationMail(int empId, String mail) {
        super(empId);
        this.mail = mail;
    }

    @Override
    public void modification(Employee employee) {
        Logger.LogInfo(this.getClass().getName(), "Modifying Mail for " + employee.getName() + " :\n"
                + employee.getMail() + " -> " + mail);
        employee.setMail(mail);
    }
}
