package be.heh.epm.domain.classification;

import be.heh.epm.domain.payDay.PayCheck;

import java.time.LocalDate;

public interface PaymentClassification {
    void CalculationSalary(PayCheck pc);

    default boolean PayPeriod(LocalDate date, PayCheck pc)
    {
        LocalDate payPeriodStart = pc.getPayPayPeriodStart(); // Récupération de la date de départ de la date de payement
        LocalDate payPeriodEnd = pc.getDate(); // Récupération de la période de fin
        return date.equals(payPeriodEnd) || date.equals(payPeriodStart) ||
                (date.isAfter(payPeriodStart) && date.isBefore(payPeriodEnd)); // renvoie True si la date se trouve entre les deux dates comprises
    }

    String toString();
    double getSalary();
    double getCommission();
}


