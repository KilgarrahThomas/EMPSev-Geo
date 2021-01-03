package be.heh.epm.domain.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class MonthlyPaymentSchedule implements PaymentSchedule {
    // ATTRIBUTES

    // CONSTRUCTOR

    // GETTERS & SETTERS

    // METHODS

    @Override
    public boolean IsDatePay(LocalDate date) {
//        Renvoie True selon 3 coffiguration possible
//        Si le dernier jour du mois est ouvrable
//        Ou si l'avant dernier jour est un vendredi
//        Ou si l'avant-avant dernier jour etst un vendredi
        boolean flag = false;
        if ((date.getDayOfMonth() == date.lengthOfMonth()) && (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY)) {
            flag = true;
        }
        else if((date.getDayOfMonth() == (date.lengthOfMonth()-1)) && (date.getDayOfWeek() == DayOfWeek.FRIDAY)) {
            flag = true;
        }
        else if((date.getDayOfMonth() == (date.lengthOfMonth()-2)) && (date.getDayOfWeek() == DayOfWeek.FRIDAY)) {
            flag = true;
        }

        return flag;
    }

    @Override
    public LocalDate GetStartPayPeriod(LocalDate dateEnd) {
        return dateEnd.withDayOfMonth(1); //Renvoie le premier jour du mois
    }
}
