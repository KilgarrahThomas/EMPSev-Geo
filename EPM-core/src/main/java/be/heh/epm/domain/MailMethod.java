package be.heh.epm.domain;

import lombok.Getter;

public class MailMethod implements PaymentMethod {

    @Getter
    private String mail;

    public MailMethod(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString(){
        return String.format("mail : %s",mail);
    }

    @Override
    public void pay(PayCheck payCheck) {
        payCheck.setField("Disposition","Mail");
    }



}
