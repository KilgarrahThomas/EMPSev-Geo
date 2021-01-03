package be.heh.epm.test;

import be.heh.epm.domain.classification.SaleReceipt;
import be.heh.epm.domain.classification.TimeCard;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.application.port.out.EmployeeGateway;
import be.heh.epm.domain.payDay.PayCheck;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class InMemoryEmployGateway implements EmployeeGateway {
    private Map<Integer, Employee> employeeMap = new HashMap<Integer, Employee>();

    @Override
    public Employee getEmployee(int id) {
        return employeeMap.get(id);
    }

    @Override
    public void createEmployee(Employee employee) {

    }

    @Override
    public void save(int id, Employee employee) {
        employeeMap.put(id, employee);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeMap.remove(id);
    }

    @Override
    public Map getAllEmployees() {
        return employeeMap;
    }

    @Override
    public void addEmployeeTimeCard(int id, TimeCard tc) {

    }

    @Override
    public HashMap getEmployeeTimeCard(int id) {
        return null;
    }

    @Override
    public void addEmployeeSaleReceipt(int id, SaleReceipt rc) {

    }

    @Override
    public HashMap getEmployeeSaleReceipt(int id) {
        return null;
    }

    @Override
    public void addPayCheck(int idemp, PayCheck pc) {

    }

    @Override
    public HashMap getPayCheckForDate(LocalDate date) {
        return null;
    }
}
