package be.heh.epm.application.service;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.classification.HourlyClassification;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.schedule.PaymentSchedule;
import be.heh.epm.domain.schedule.WeeklyPaymentSchedule;

public class AddHourlyEmployee extends AddEmployee{

    // ATTRIBUTES
    double salary;

    // CONSTRUCTOR
    public AddHourlyEmployee(int id, String name, String address, String mail, double salary) {
        super(id, name, address, mail);
        this.salary = salary;
        Logger.LogInfo(this.getClass().getName(), "New Hourlied created : " + name);
    }


    // GETTERS & SETTERS

    // METHODS
    @Override
    // Défini la classification sur Payé à l'heure et la retourne
    public PaymentClassification makePayClassification() {
        return new HourlyClassification(salary);
    }

    @Override
    // Définit le schéma de payement sur Payé toutes les semaines et la retourne
    public PaymentSchedule makePaySchedule() {
        return new WeeklyPaymentSchedule();
    }
}
