package be.heh.epm.domain.payDay;

import java.time.LocalDate;

public class PayCheck {

    private LocalDate date;
    private LocalDate PayPayPeriodStart;
    private double salary;
    private String method;

    public PayCheck(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setPayPayPeriodStart(LocalDate payPayPeriodStart) {
        PayPayPeriodStart = payPayPeriodStart;
    }

    public LocalDate getPayPayPeriodStart() {
        return PayPayPeriodStart;
    }

    public double getSalary() {
        return salary;
    }

    public void setPay(double salary) {
        this.salary = salary;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
