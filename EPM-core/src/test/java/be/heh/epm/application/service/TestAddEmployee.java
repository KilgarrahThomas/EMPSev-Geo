package be.heh.epm.application.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import be.heh.epm.application.port.in.AddEmployeeUseCase;
import be.heh.epm.application.port.in.EmployeeSalariedValidating;
import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.domain.*;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestAddEmployee {

    private EmployeePort employeePort = Mockito.mock(EmployeePort.class);
    private Employee employee = Mockito.mock(Employee.class);

   /* @Before
    public void setUp() {
        employeeGateway = new InMemoryEmployeeGateway();
    }*/

    @Test
    public void testAddSalariedEmployee() {

        //AddEmployeeSalariedValidating addEmployeeSalariedValidating = new AddEmployeeSalariedValidating(1, "Bob", "Home", "toto@gmail.com", 1500);
        AddEmployeeUseCase addSalariedEmployee = new AddSalariedEmployeeService(employeePort);

        EmployeeSalariedValidating employeeSalariedValidating = new EmployeeSalariedValidating();
        employeeSalariedValidating.setEmpId(1);
        employeeSalariedValidating.setName("toto");
        employeeSalariedValidating.setAddress("rue de Mons");
        employeeSalariedValidating.setMail("toto@heh.be");
        employeeSalariedValidating.setMonthlySalary(1500);

        addSalariedEmployee.execute(employeeSalariedValidating);

        //verify(employeePort).save(refEq(employee));

        /*Employee e = employeeGateway.getEmployee(employeeSalariedValidating.getEmpId());
        assertEquals("toto", e.getName());

        PaymentSchedule ps = e.getPaySchedule();
        assertTrue(ps instanceof MonthlyPaymentSchedule);

        PaymentMethod pm = e.getPayMethod();
        assertEquals("direct deposit into Fortis : be332211", pm.toString());*/
    }
/*
    @Ignore
    @Test
    public void testAddHourlyEmployee() {
        int empId = 2;
        AddHourlyEmployee t = new AddHourlyEmployee(empId, "BobHourly", "Home_Hourly","toto@gmail.com", 20.0,employeeGateway);
        t.execute();

        Employee e = employeeGateway.getEmployee(empId);
        assertEquals("BobHourly", e.getName());

        PaymentSchedule ps = e.getPaySchedule();
        assertTrue(ps instanceof WeeklyPaymentSchedule);

        PaymentMethod pm = e.getPayMethod();
        assertEquals("direct deposit into Fortis : be332211", pm.toString());
    }
    */

}
