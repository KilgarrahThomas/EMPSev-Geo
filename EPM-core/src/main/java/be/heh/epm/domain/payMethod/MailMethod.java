package be.heh.epm.domain.payMethod;

public class MailMethod implements PaymentMethod {
    // ATTRIBUTES
    private String mail;

    // CONSTRUCTOR

    public MailMethod(String mail) {
        this.mail = mail;
    }

    // GETTERS & SETTERS

    @Override
    public String getBank() {
        return null;
    }

    @Override
    public String getAccount() {
        return null;
    }


    // METHODS


    @Override
    public String toDB() {
        return "mail";
    }

    @Override
    public String toString() {
        return "mail : " + this.mail;
    }
}
