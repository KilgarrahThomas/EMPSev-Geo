package be.heh.epm.domain;

import java.time.LocalDate;
//import java.util.Calendar; ce n'est plus utilisé

public interface PaymentSchedule {
    boolean isPayDate(LocalDate payDate);

    @Override
    String toString();
}