package be.heh.epm.database;

import java.time.LocalDate;
import java.time.ZoneId;
import java.sql.Date;
import java.time.format.DateTimeFormatter;

public class DateConvert {

// ATTRIBUTES

// CONSTRUCTOR

// GETTERS & SETTERS

// METHODS

    public static LocalDate DateToLocalDate(Date date) {
        LocalDate LD = date.toLocalDate();
        return LD;
    }

    public static Date LocalDateToDate (LocalDate LD) {
        return Date.valueOf(LD);
    }
}
