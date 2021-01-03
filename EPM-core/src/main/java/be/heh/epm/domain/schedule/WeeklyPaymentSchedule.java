package be.heh.epm.domain.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeeklyPaymentSchedule implements PaymentSchedule{
    // ATTRIBUTES

    // CONSTRUCTOR

    // GETTERS & SETTERS

    // METHODS
    @Override
    public boolean IsDatePay(LocalDate date) {
        // Renvoie vrai si le jour est un vendredi
        return (date.getDayOfWeek() == DayOfWeek.FRIDAY);
    }

    @Override
    public LocalDate GetStartPayPeriod(LocalDate dateEnd) {
        return dateEnd.minusDays(4); // Renvoie le lundi
    }
}
