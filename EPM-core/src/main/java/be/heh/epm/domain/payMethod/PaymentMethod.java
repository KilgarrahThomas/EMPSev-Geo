package be.heh.epm.domain.payMethod;

public interface PaymentMethod {
    public String toDB();
    public String getBank();
    public String getAccount();
}
