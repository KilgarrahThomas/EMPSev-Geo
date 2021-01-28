package be.heh.epm.application.service;

import be.heh.epm.application.port.in.AddEmployeeUseCase;
import be.heh.epm.application.port.in.EmployeeHourlyValidating;
import be.heh.epm.application.port.in.EmployeeValidating;
import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.common.UseCase;
import be.heh.epm.domain.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@UseCase
@Component("Hourly")
public class AddHourlyEmployeeService implements AddEmployeeUseCase {

// ATTRIBUTES
    private EmployeePort employeePort;

// CONSTRUCTOR

    public AddHourlyEmployeeService(EmployeePort employeePort) {
        this.employeePort = employeePort;
    }

// GETTERS & SETTERS

// METHODS

    @Override
    public void execute(EmployeeValidating EmployeeValidating) {
        EmployeeHourlyValidating hourlied = (EmployeeHourlyValidating) EmployeeValidating;

        PaymentClassification pc = new HourlyClassification(hourlied.getHourlyRate());
        PaymentSchedule ps = new WeeklyPaymentSchedule();
        PaymentMethod pm = new DirectDepositMethod("Fortis","be332211");

        Employee e = new Employee(hourlied.getName(),hourlied.getAddress(),hourlied.getMail());
        e.setPayClassification(pc);
        e.setPaySchedule(ps);
        e.setPayMethod(pm);

        Employee employee = employeePort.save(e);
    }
}
