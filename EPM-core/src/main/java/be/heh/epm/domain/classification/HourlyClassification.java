package be.heh.epm.domain.classification;

import java.time.LocalDate;
import java.util.HashMap;
import be.heh.epm.domain.payDay.PayCheck;

public class HourlyClassification implements PaymentClassification {
    // ATTRIBUTES
    private double hoursSalary;
    private HashMap<LocalDate, TimeCard> listTimeCards;

    // CONSTRUCTOR
    public HourlyClassification(double hoursSalary) {
        this.hoursSalary = hoursSalary;
        listTimeCards = new HashMap<LocalDate, TimeCard>();
    }

    // GETTERS & SETTERS
    public void addTimeCard(TimeCard TC) {
        listTimeCards.put(TC.getDate(), TC);
    }

    public TimeCard getTimeCard(LocalDate date) {
        return listTimeCards.get(date);
    }

    public void setListTimeCards(HashMap<LocalDate, TimeCard> listTimeCards) {
        this.listTimeCards = listTimeCards;
    }

    @Override
    public double getSalary() {
        return hoursSalary;
    }

    @Override
    public double getCommission() {
        return 0;
    }

    // METHODS
    private double CalculSalaryTimeCard(TimeCard TC) {
        // Calcul du salaire journalier
        double hours = TC.getHours(); // On récupère le nombre d'heures prestée
        double overtime = Math.max(0.0, hours-8.0); // On récupère le nombre d'heures supp, s'il y en a
        double straightTime = hours - overtime; // Calcul du temps presté, sans les heures supplémentaires
        return (straightTime + overtime * 1.5) * this.hoursSalary; // On retourne le salaire journalier
    }

    @Override
    public void CalculationSalary(PayCheck pc) {
        double totalpay = 0.0; // On pose le salaire à 0
        for(TimeCard timeCard :listTimeCards.values()){ // On récupère les TimeCards de l'employé
            if(PayPeriod(timeCard.getDate(),pc)){ // Si la TimeCard est dans la période de payement
                totalpay += CalculSalaryTimeCard(timeCard); // On calcule le salire du jour presté et on l'ajoute au total
            }
        }
        pc.setPay(totalpay); // On envoie le total de la paie
        return;
    }

    @Override
    public String toString() {
        return "hourlied";
    }
}
