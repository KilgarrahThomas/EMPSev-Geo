package be.heh.epm.application.service.modification;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.application.service.Command;
import be.heh.epm.application.Context;
import be.heh.epm.domain.employee.Employee;

public abstract class ModificationEmployee implements Command {
    private int empId;

    public ModificationEmployee(int empId) {
        this.empId = empId;
    }

    public abstract void modification(Employee employee);

    @Override
    public void execute() {
        Logger.LogInfo(this.getClass().getName(), "Asking to recover Employee " + empId);
        Employee e = Context.emp.getEmployee(empId);
        if (e != null) {
            Logger.LogInfo(this.getClass().getName(), "Employee " + e.getName() + " recovered");
            modification(e);
            Logger.LogInfo(this.getClass().getName(), "Asking to save modifications");
            Context.emp.save(e.getEmpID(), e);
        }
        else {
            Logger.LogError(this.getClass().getName(), "Employee not recovered");
        }
    }
}
