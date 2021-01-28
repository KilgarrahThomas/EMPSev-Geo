package be.heh.epm.application.service;

import be.heh.epm.application.port.in.AddEmployeeUseCase;
import be.heh.epm.application.port.in.EmployeeSalariedValidating;
import be.heh.epm.application.port.in.EmployeeValidating;
import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.common.UseCase;
import be.heh.epm.domain.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@UseCase
@Component("Salaried")
public class AddSalariedEmployeeService implements AddEmployeeUseCase {

    //private AddEmployeeSalariedValidating addEmployeeSalariedValidating;
    private EmployeePort employeePort;

    public AddSalariedEmployeeService(EmployeePort employeePort) {
        this.employeePort = employeePort;
    }

    public void execute(EmployeeValidating employeeSalariedValidating) {
        EmployeeSalariedValidating salaried = (EmployeeSalariedValidating) employeeSalariedValidating;

        PaymentClassification pc = new SalariedClassification(salaried.getMonthlySalary());
        PaymentSchedule ps = new MonthlyPaymentSchedule();
        PaymentMethod pm = new DirectDepositMethod("Fortis","be332211");

        Employee e = new Employee(salaried.getName(),salaried.getAddress(),salaried.getMail());
        e.setPayClassification(pc);
        e.setPaySchedule(ps);
        e.setPayMethod(pm);

        Employee employee = employeePort.save(e);

    }

}
