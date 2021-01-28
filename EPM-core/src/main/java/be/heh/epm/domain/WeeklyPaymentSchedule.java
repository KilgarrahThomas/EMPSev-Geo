package be.heh.epm.domain;

import java.lang.annotation.Annotation;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

public class WeeklyPaymentSchedule implements PaymentSchedule {
    public final String schedule = "Weekly";

    @Override
    public boolean isPayDate(LocalDate payDate) {
        DayOfWeek dayOfWeek = payDate.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.FRIDAY);
    }

    /*@Override
    public String getType() {
        return "weekly";
    }*/

    @Override
    public String toString(){
        return schedule;
    }


}
