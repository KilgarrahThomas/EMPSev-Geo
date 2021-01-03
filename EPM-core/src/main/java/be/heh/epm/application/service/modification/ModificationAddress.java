package be.heh.epm.application.service.modification;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.employee.Employee;

public class ModificationAddress extends ModificationEmployee{
    private String address;

    public ModificationAddress(int empId, String address) {
        super(empId);
        this.address = address;
    }

    @Override
    public void modification(Employee employee) {
        Logger.LogInfo(this.getClass().getName(), "Modifying Address for " + employee.getName() + " :\n"
        + employee.getAddress() + " -> " + address);
        employee.setAddress(address);
    }
}
