package be.heh.epm.domain;

import lombok.Getter;

public class DirectDepositMethod implements PaymentMethod {

    @Getter
    private String bank;
    @Getter
    private String accountNumber;

    public DirectDepositMethod(String bank, String accountNumber) {
        this.bank = bank;
        this.accountNumber = accountNumber;
    }

    public String toString(){
        return "direct";
    }

    @Override
    public void pay(PayCheck payCheck) {
        payCheck.setField("Disposition","Bank");
    }

}
