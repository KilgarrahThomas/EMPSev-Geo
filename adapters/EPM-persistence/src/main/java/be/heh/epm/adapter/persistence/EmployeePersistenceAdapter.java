package be.heh.epm.adapter.persistence;

import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.common.PersistenceAdapter;
import be.heh.epm.common.WebAdapter;
import be.heh.epm.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@PersistenceAdapter
@WebAdapter
public class EmployeePersistenceAdapter implements EmployeePort {

    private static final Logger logger = LoggerFactory.getLogger(EmployeePersistenceAdapter.class);

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsertEmployee;
    private final SimpleJdbcInsert simpleJdbcInsertSalariedClassification;
    private final SimpleJdbcInsert simpleJdbcInsertHourliedClassification;
    private final DataSource dataSource;

    public EmployeePersistenceAdapter(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
        this.simpleJdbcInsertEmployee = new SimpleJdbcInsert(dataSource).withTableName("employee")
                .usingGeneratedKeyColumns("empid");
        this.simpleJdbcInsertSalariedClassification = new SimpleJdbcInsert(dataSource).withTableName("salariedclassification");
        this.simpleJdbcInsertHourliedClassification = new SimpleJdbcInsert(dataSource).withTableName("hourliedclassification");
    }

    @Override
    public Employee getEmployee(int empID) {
        try {
            Employee employee = jdbcTemplate.queryForObject("SELECT * FROM EMPLOYEE WHERE EMPID = ?",
                    new Object[]{empID},
                    new EmployeeMapper());
            logger.info("Recovery of the employee by id {} in the database",empID);
            return employee;
        } catch (EmptyResultDataAccessException e) {
            logger.error("Employee with id {} was not found",empID);
            return null;
        }
    }

    @Override
    public Employee save(Employee employee) {

        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", employee.getName());
        parameters.put("address", employee.getAddress());
        parameters.put("mail", employee.getMail());
        parameters.put("paymentclassificationtype",employee.getPayClassification().toString());
        parameters.put("paymentmethodtype",employee.getPayMethod().toString());
        parameters.put("paymentscheduletype",employee.getPaySchedule().toString());

        // Execute the query and get the generated key
        Number newId = simpleJdbcInsertEmployee.executeAndReturnKey(parameters);

        logger.info("Inserting salaried employee into database, generated key is: {}", newId);
        employee.setEmpID((Integer) newId);


        if(employee.getPayClassification().toString()=="salaried"){
            Map<String, Object> parametersSalariedClassification = new HashMap<>(1);
            parametersSalariedClassification.put("EMPID", employee.getEmpID());
            SalariedClassification salariedClassification = (SalariedClassification)employee.getPayClassification();
            parametersSalariedClassification.put("MOUNT", salariedClassification.getSalary());
            simpleJdbcInsertSalariedClassification.execute(parametersSalariedClassification);
        }

        if(employee.getPayClassification().toString()=="hourly"){
            Map<String, Object> parametersHourlyClassification = new HashMap<>(1);
            parametersHourlyClassification.put("EMPID", employee.getEmpID());
            HourlyClassification hourlyClassification = (HourlyClassification) employee.getPayClassification();
            parametersHourlyClassification.put("HOURLYRATE", hourlyClassification.getHourlyRate());
            simpleJdbcInsertHourliedClassification.execute(parametersHourlyClassification);
        }

        return employee;
    }

    @Override
    public void deleteEmployee(int empID) {
        int resSalaried, resHourly, res2;
        resSalaried = jdbcTemplate.update("DELETE FROM salariedClassification WHERE EMPID = ?", empID);
        resHourly = jdbcTemplate.update("DELETE FROM hourliedClassification WHERE EMPID = ?", empID);
        res2 = jdbcTemplate.update("DELETE FROM EMPLOYEE WHERE EMPID = ?", empID);
        if ((resSalaried == 1 || resHourly == 1) && res2 == 1) {
            logger.info("Deleted mployee {} from database", empID);
        }
        else {
            logger.error("No Employee for id {}", empID);
        }

    }

    @Override
    public ArrayList<Employee> getAllEmployee() {

        return (ArrayList<Employee>) jdbcTemplate.query("SELECT * FROM EMPLOYEE", new EmployeeMapper());
    }

    private class EmployeeMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee e = new Employee();
            e.setEmpID(rs.getInt("EMPID"));
            e.setName(rs.getString("NAME"));
            e.setAddress(rs.getString("ADDRESS"));
            e.setMail(rs.getString("MAIL"));

            String classification = rs.getString("paymentClassificationType");
            logger.info("Recovering Classification");
            switch (classification) {
                case "salaried":
                    SalariedClassification sc = jdbcTemplate.queryForObject("SELECT * FROM salariedClassification where EMPID = ?",
                            new Object[]{e.getEmpID()},
                            (rs2, rowNum2) -> {
                                return new SalariedClassification(rs2.getDouble("MOUNT"));
                            });
                    e.setPayClassification(sc);

                    break;
                case "hourly" :
                    HourlyClassification hc = jdbcTemplate.queryForObject("SELECT * FROM hourliedClassification where EMPID = ?",
                            new Object[]{e.getEmpID()},
                            (rs2, rowNum2) -> {
                                return new HourlyClassification(rs2.getDouble("HOURLYRATE"));
                            });
                    e.setPayClassification(hc);
                    break;
                default:
                    logger.error("Cannot recover Classification for Employee {}", e.getEmpID());
                    break;
            }

            String schedule = rs.getString("paymentScheduleType");
            logger.info("Recovering Schedule");
            switch (schedule) {
                case "Monthly":
                    e.setPaySchedule(new MonthlyPaymentSchedule());
                    break;
                case "Weekly" :
                    e.setPaySchedule(new WeeklyPaymentSchedule());
                    break;
                default:
                    logger.error("Cannot recover Schedule for Employee {}", e.getEmpID());
                    break;
            }

            String payMethod = rs.getString("paymentMethodType");
            logger.info("Recovering PayMethod");
            switch (payMethod) {
                case "direct":
                    DirectDepositMethod ddm = new DirectDepositMethod("Fortis","be332211");
                    e.setPayMethod(ddm);
                    break;
                default:
                    MailMethod mm = new MailMethod(e.getMail());
                    e.setPayMethod(mm);
                    break;
            }

            return e;
        }
    }
}
