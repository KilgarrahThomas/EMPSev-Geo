package be.heh.epm.domain;

import lombok.Getter;

public class SalariedClassification implements PaymentClassification{

    @Getter
    private double salary;

    public SalariedClassification(double salary) {
        this.salary = salary;
    }

    @Override
    public double calculatePay(PayCheck paychek) {
        return salary;
    }

    public String toString(){
        return "salaried";
    }

}
