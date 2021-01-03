package be.heh.epm.application.service;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.classification.CommissionClassification;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.schedule.PaymentSchedule;
import be.heh.epm.domain.schedule.TwoWeeksPayementSchedule;

public class AddCommissionedEmployee extends AddEmployee {

    // ATTRIBUTES
    double salary;
    double commission;

    // CONSTRUCTOR
    public AddCommissionedEmployee(int id, String name, String address, String mail, double salary, double commission) {
        super(id, name, address, mail);
        this.salary = salary;
        this.commission = commission;
        Logger.LogInfo(this.getClass().getName(), "New SCommissionned created : " + name);
    }

    // GETTERS & SETTERS

    // METHODS
    @Override
    // Défini la classification sur Commissionned et la retourne
    public PaymentClassification makePayClassification() {
        return new CommissionClassification(salary, commission);
    }

    @Override
    // Définit le schéma de payement sur 1 vendredi sur 2 et la retourne
    public PaymentSchedule makePaySchedule() {
        return new TwoWeeksPayementSchedule();
    }
}
