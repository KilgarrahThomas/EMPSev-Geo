package be.heh.epm.adapter.persistence;

import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.domain.DirectDepositMethod;
import be.heh.epm.domain.Employee;
import be.heh.epm.domain.MonthlyPaymentSchedule;
import be.heh.epm.domain.SalariedClassification;
import com.github.database.rider.core.api.connection.ConnectionHolder;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;

@SpringBootTest
public class EmployeePersistenceAdapterTest {


    @Autowired
    private DataSource dataSource;

    @Autowired
    private EmployeePersistenceAdapter employeePersistenceAdapter;

    @Test
    void SalariedEmployeeSaveTest() {
        //JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //employeePersistenceAdapter = new EmployeePersistenceAdapter(jdbcTemplate,dataSource);
        Employee salariedEmployee = new Employee("toto", "rue de Mons", "toto@heh.be");
        salariedEmployee.setPayClassification(new SalariedClassification(1500));
        salariedEmployee.setPayMethod(new DirectDepositMethod("ING", "BE5555555555"));
        salariedEmployee.setPaySchedule(new MonthlyPaymentSchedule());
        try {
            Employee SavedEmployee = employeePersistenceAdapter.save(salariedEmployee);
            Assertions.assertEquals("toto", SavedEmployee.getName());
            Assertions.assertEquals("rue de Mons", SavedEmployee.getAddress());

            Employee loadedEmployee = employeePersistenceAdapter.getEmployee(SavedEmployee.getEmpID());
            Assertions.assertEquals("toto", loadedEmployee.getName(), "Employee name does not match");
            Assertions.assertEquals("toto@heh.be", loadedEmployee.getMail(), "Employee mail does not match");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
