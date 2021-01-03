package be.heh.epm.application.service;

import be.heh.epm.application.Context;
import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.classification.CommissionClassification;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.classification.SaleReceipt;
import be.heh.epm.domain.employee.Employee;

import java.time.LocalDate;

public class AddSalesReceipt implements Command {

    // ATTRIBUTES
    int empId;
    LocalDate date;
    double saleAmount;
    SaleReceipt sr;

    // CONSTRUCTOR
    public AddSalesReceipt(int empId, LocalDate date, double saleAmount) {
        this.empId = empId;
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
    @Override
    public void execute() {
        // Récupérer l'enmployé
        Employee e = Context.emp.getEmployee(empId);
        if(e != null) { // Si l'employé existe...
            Logger.LogInfo(this.getClass().getName(), e.getName() + " recovered");
            PaymentClassification pc = e.getPayClassification(); // On récupère sa classification
            Logger.LogInfo(this.getClass().getName(), "classification recovered");
            if (pc instanceof CommissionClassification) { // S'il touche des commissions
                CommissionClassification cc = (CommissionClassification) pc;
                sr = new SaleReceipt(date, saleAmount); // On crée la reçu de vente
                cc.addSaleReceipt(sr); // Et on l'ajoute
                Context.emp.addEmployeeSaleReceipt(e.getEmpID(), sr); // Et on envoie pour la sauvegarde
            }
            else { // S'il ne touche pas de commissions
                Logger.LogError(this.getClass().getName(), e.getName() + " isn't a commissionned employee");
            }
        }
        else{ // Si l'employé n'existe pas
            Logger.LogError(this.getClass().getName(), "No employee for ID " + empId);
        }
    }
}
