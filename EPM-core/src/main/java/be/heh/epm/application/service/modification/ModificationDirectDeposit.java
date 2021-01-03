package be.heh.epm.application.service.modification;

import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.payMethod.DirectDepositMethod;
import be.heh.epm.domain.payMethod.PaymentMethod;

public class ModificationDirectDeposit extends ModificationPayMethod {
    // ATTRIBUTES
    private String bank;
    private String account;

    public ModificationDirectDeposit(int empId, String bank, String account) {
        super(empId);
        this.bank = bank;
        this.account = account;
    }

    @Override
    public PaymentMethod getMethod() {
        Logger.LogInfo(this.getClass().getName(), "PayMethod: Bank transfert to " + bank + " - " + account);
        return new DirectDepositMethod(bank, account);
    }
}
