package be.heh.epm.domain;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;

public class MonthlyPaymentSchedule implements PaymentSchedule {
    public final String schedule = "Monthly";

    public boolean isPayDate(LocalDate date){
        return isLastDayOfMonth(date);
    }

   /* @Override
    public String getType() {
        return "monthly";
    }*/

    private boolean isLastDayOfMonth(LocalDate date) {

        return getLastDayOfMonth(date).equals(date);
    }

    private LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    @Override
    public String toString(){
        return schedule;
    }

}
