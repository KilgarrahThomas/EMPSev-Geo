package be.heh.epm.domain.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.*;
import java.util.Locale;

public class TwoWeeksPayementSchedule implements PaymentSchedule{
    // ATTRIBUTES

    // CONSTRUCTOR

    // GETTERS & SETTERS

    // METHODS
    private int WeekNumber(LocalDate date){
        TemporalField wf = WeekFields.of(Locale.getDefault()).weekOfMonth(); // On définit ce qu'on veut récupérer. Ici le numéro de la semaine
        return date.get(wf); // On renvoie le numéro de la semaine de la date transmise en paramètre
    }

    @Override
    public boolean IsDatePay(LocalDate date) {
        int weekNumber = WeekNumber(date); //Récupération du numéro de la semaine de la date
        // Renvoie True si c'est le 2eme ou le 4ème vendredi du mois
        return (weekNumber == 2 || weekNumber == 4) && (date.getDayOfWeek() ==  DayOfWeek.FRIDAY);
    }

    @Override
    public LocalDate GetStartPayPeriod(LocalDate dateEnd) {
        LocalDate tmp = dateEnd.minusDays(14); // On récupère le vendredi d'il y a 2 semaines
        if (!IsDatePay(tmp)){ // Si ce vendredi n'était pas un jour de paie
            tmp = tmp.minusDays(7); // On remonte encore une semaine
        }
        return tmp.plusDays(3); // On avance de 3 jour pour obtenir le lundi de départ
    }
}
