package be.heh.epm.application.service;

import be.heh.epm.application.Context;
import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.classification.HourlyClassification;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.classification.TimeCard;
import be.heh.epm.domain.employee.Employee;

import java.time.LocalDate;

public class AddTimeCard implements Command {

    // ATTRIBUTES
    int empId;
    LocalDate date;
    double hours;
    TimeCard tc;

    // CONSTRUCTOR
    public AddTimeCard(int empId, LocalDate date, double hours) {
        this.empId = empId;
        this.date = date;
        this.hours = hours;
    }

    // GETTERS & SETTERS
    public LocalDate getDate() {
        return date;
    }

    public double getHours() {
        return hours;
    }

    // METHODS
    @Override
    public void execute() {
        // Récupérer l'enmployé
        Employee e = Context.emp.getEmployee(empId);
        if(e != null) { // Si l'employé existe...
            Logger.LogInfo(this.getClass().getName(), e.getName() + " recovered");
            PaymentClassification pc = e.getPayClassification(); // On récupère sa classification
            Logger.LogInfo(this.getClass().getName(), "classification recovered");
            if (pc instanceof HourlyClassification) { // S'il est payé à l'heure
                HourlyClassification hc = (HourlyClassification) pc;
                tc = new TimeCard(date, hours); // On lui crée sa TimeCard
                hc.addTimeCard(tc); // On lui ajoute
                Context.emp.addEmployeeTimeCard(e.getEmpID(), tc); // Et on envoie pour la sauvegarde
            }
            else { // S'il n'est pas payé à l'heure
                Logger.LogError(this.getClass().getName(), e.getName() + " isn't a hourly employee");
            }
        }
        else{ // S'il n'existe pas
            Logger.LogError(this.getClass().getName(), "No employee for ID " + empId);
        }
    }
}
