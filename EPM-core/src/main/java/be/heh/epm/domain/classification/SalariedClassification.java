package be.heh.epm.domain.classification;

import be.heh.epm.domain.payDay.PayCheck;

public class SalariedClassification implements PaymentClassification {
    // ATTRIBUTES
    private double salary;

    // CONSTRUCTOR

    public SalariedClassification(double salary) {
        this.salary = salary;
    }

    // GETTERS & SETTERS

    public double getSalary() {
        return salary;
    }

    @Override
    public double getCommission() {
        return 0;
    }

    // METHODS

    @Override
    public void CalculationSalary(PayCheck pc) {
        pc.setPay(this.salary); // Retourne le salaire défini de l'employé
        return;
    }

    @Override
    public String toString() {
        return "salaried";
    }
}
