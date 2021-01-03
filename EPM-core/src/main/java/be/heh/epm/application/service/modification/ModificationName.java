package be.heh.epm.application.service.modification;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.employee.Employee;

public class ModificationName extends ModificationEmployee{
    private String name;

    public ModificationName(int empId, String name) {
        super(empId);
        this.name = name;
    }

    @Override
    public void modification(Employee employee) {
        Logger.LogInfo(this.getClass().getName(), "Modifying Name for " + employee.getName() + " :\n"
                + employee.getName() + " -> " + name);
        employee.setName(name);
    }
}
