package be.heh.epm.application.service;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.classification.SalariedClassification;
import be.heh.epm.domain.schedule.MonthlyPaymentSchedule;
import be.heh.epm.domain.schedule.PaymentSchedule;

public class AddSalariedEmployee extends AddEmployee{

    //ATTRIBUTES
    double salary;

    // CONSTRUCTORS
    public AddSalariedEmployee(int id, String name, String address, String mail, double salary) {
        super(id, name, address, mail);
        this.salary = salary;
        Logger.LogInfo(this.getClass().getName(), "New Salararied created : " + name);
    }

    // GETTERS & SETTERS

    // METHODS
    @Override
    // Défini l'employé comme Salarié et la retourne
    public PaymentClassification makePayClassification() {
        return new SalariedClassification(salary);
    }

    @Override
    // Défini l'employé comme payé par mois
    public PaymentSchedule makePaySchedule() {
        return new MonthlyPaymentSchedule();
    }
}
