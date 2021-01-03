package be.heh.epm.application.service;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.application.service.Command;
import be.heh.epm.application.Context;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.domain.payDay.PayCheck;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PayDay implements Command {
    LocalDate date;
    Map<Integer, PayCheck> payCheckList = new HashMap<Integer, PayCheck>();

    public PayDay(LocalDate date) {
        this.date = date;
    }

    @Override
    public void execute() {
        Logger.LogInfo(this.getClass().getName(), "Employees recovering");
        TreeMap<Integer, Employee> employeeList = (TreeMap<Integer, Employee>) Context.emp.getAllEmployees();
        Logger.LogInfo(this.getClass().getName(), "Employees recovered");
        for (Map.Entry<Integer, Employee> entry : employeeList.entrySet()) {
            int empID = entry.getKey();
            Employee e = entry.getValue();
            if(e.isDatePay(date)) {
                Logger.LogInfo(this.getClass().getName(), "Is Payday for " + e.getName());
                PayCheck pc = new PayCheck(date);
                e.payDay(pc);
                Context.emp.addPayCheck(empID, pc);
                //payCheckList.put(empID, pc);
            }
            else Logger.LogInfo(this.getClass().getName(), "Isn't Payday for " + e.getName());
        };
    }

    public PayCheck getPayCheck (int empID, LocalDate date) {
        return (PayCheck) getPayChekList(date).get(Context.emp.getEmployee(empID));
    }

    public Map getPayChekList(LocalDate date) {
        return Context.emp.getPayCheckForDate(date);
    }
}
