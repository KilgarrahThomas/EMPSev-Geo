package be.heh.epm.application.port.out;

import be.heh.epm.domain.classification.SaleReceipt;
import be.heh.epm.domain.classification.TimeCard;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.domain.payDay.PayCheck;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public interface EmployeeGateway {
    // DECLARED METHODS
    public Employee getEmployee(int id); // Va chercher un employé selon son ID

    public void createEmployee(Employee employee); // Creation d'un nouvel employé

    public void save(int id, Employee employee); // Sauvegarde un employé à l'ID définie

    public void deleteEmployee(int id); // Supprime l'employé indiqué par l'ID

    public Map getAllEmployees(); // Va chercher tous les employés

    public void addEmployeeTimeCard(int id, TimeCard tc);

    public HashMap getEmployeeTimeCard(int id);

    public void addEmployeeSaleReceipt(int id, SaleReceipt rc);

    public HashMap getEmployeeSaleReceipt(int id);

    public void addPayCheck(int idemp, PayCheck pc);

    public HashMap getPayCheckForDate(LocalDate date);
}
