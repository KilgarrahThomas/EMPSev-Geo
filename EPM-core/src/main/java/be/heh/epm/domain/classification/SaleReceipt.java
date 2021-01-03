package be.heh.epm.domain.classification;

import java.time.LocalDate;

public class SaleReceipt {
    // ATTRIBUTES
    private LocalDate date;
    private double saleAmount;

    // CONSTRUCTOR
    public SaleReceipt(LocalDate date, double saleAmount) {
        this.date = date;
        this.saleAmount = saleAmount;
    }

    // GETTERS & SETTERS
    public LocalDate getDate() {
        return date;
    }

    public double getSaleAmount() {
        return saleAmount;
    }

    // METHODS
}
