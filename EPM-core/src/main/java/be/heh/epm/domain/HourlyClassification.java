package be.heh.epm.domain;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HourlyClassification implements PaymentClassification {

    @Getter
    private double hourlyRate;
    private Map<LocalDate,TimeCard> timeCardMap = new HashMap<>();

    public HourlyClassification(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculatePay(PayCheck paycheck) {
        double totalPay = 0;
        for (TimeCard timeCard : timeCardMap.values() ) {
            if(isInPayPeriod(timeCard,paycheck.getDate()))
            totalPay += calculatePayForTimeCard(timeCard);
        }
        return totalPay;
    }

   /* @Override
    public String getType() {
        return "hourly";
    }*/

    private boolean isInPayPeriod(TimeCard card, LocalDate payPeriod) {
        LocalDate payPeriodEndDate = payPeriod;
        LocalDate payPeriodStartDate = payPeriod.minusDays(5);
        return card.getDate().isAfter(payPeriodStartDate) || card.getDate().isEqual(payPeriodStartDate) &&
                card.getDate().isBefore(payPeriodEndDate) || card.getDate().isEqual(payPeriodEndDate);
    }

    public void addTimeCard(TimeCard timeCard){
        timeCardMap.put(timeCard.getDate(),timeCard);
    }

    public TimeCard getTimeCard(LocalDate date) {
        return timeCardMap.get(date);
    }

    private double calculatePayForTimeCard(TimeCard timeCard) {
        double hours = timeCard.getHours();
        double overtime = Math.max(0.0,hours-8.0);
        double straightTime = hours - overtime;
        return straightTime*hourlyRate + overtime*hourlyRate*1.5;
    }

    @Override
    public String toString(){
        return "hourly";
    }
}
