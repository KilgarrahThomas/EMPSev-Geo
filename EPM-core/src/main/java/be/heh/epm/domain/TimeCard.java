package be.heh.epm.domain;

import java.time.LocalDate;
import java.util.Calendar;

public class TimeCard {

    private LocalDate date;
    private double hours;

    public LocalDate getDate() {
        return date;
    }

    public double getHours() {
        return hours;
    }

    public TimeCard(LocalDate date, double hours) {
        this.date = date;
        this.hours = hours;
    }

}
