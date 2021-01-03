package be.heh.epm.domain.payMethod;

public class DirectDepositMethod implements PaymentMethod {
    // ATTRIBUTES
    private String bank;
    private String account;

    // CONSTRUCTOR

    public DirectDepositMethod(String bank, String account) {
        this.bank = bank;
        this.account = account;
    }

    // GETTERS & SETTERS

    public String getBank() {
        return bank;
    }

    public String getAccount() {
        return account;
    }


    // METHODS


    @Override
    public String toDB() {
        return "directdeposit";
    }

    @Override
    public String toString() {
        return "direct deposit into " + this.bank + " : " + this.account;
    }
}
