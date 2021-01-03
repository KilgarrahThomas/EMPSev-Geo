package be.heh.epm.domain.schedule;

import java.time.LocalDate;

public interface PaymentSchedule {

    public boolean IsDatePay(LocalDate date);

    public LocalDate GetStartPayPeriod(LocalDate dateEnd);
}
