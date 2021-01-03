package be.heh.epm.domain.classification;

import be.heh.epm.domain.payDay.PayCheck;

import java.time.LocalDate;
import java.util.HashMap;

public class CommissionClassification implements PaymentClassification {
    // ATTRIBUTES
    private double salary;
    private double commission;
    private HashMap<LocalDate, SaleReceipt> listSaleReceipt = new HashMap<LocalDate, SaleReceipt>();

    // CONSTRUCTOR
    public CommissionClassification(double salary, double commission) {
        this.salary = salary;
        this.commission = commission;
    }

    // GETTERS & SETTERS
    public void addSaleReceipt(SaleReceipt salesReceipt)
    {
        this.listSaleReceipt.put(salesReceipt.getDate(), salesReceipt);
    }

    public SaleReceipt geSaleReceipt(LocalDate date) {
        return listSaleReceipt.get(date);
    }

    public void setListSaleReceipt(HashMap<LocalDate, SaleReceipt> listSaleReceipt) {
        this.listSaleReceipt = listSaleReceipt;
    }

    public double getSalary() {
        return salary;
    }

    public double getCommission() {
        return commission;
    }

    // METHODS

    private double CalculCommission(SaleReceipt SR){
        // Calcul de la part revenue à l'employée à partir du % défini pour l'employé
        return (this.commission * SR.getSaleAmount())/100;
    }

    @Override
    public void CalculationSalary(PayCheck pc) {
        // Calcul du salaire de l'employé
        double totalpay = this.salary; // On pose le salaire de base
        for(SaleReceipt saleReceipt : listSaleReceipt.values()){ // On boucle sur tous ses reçus de ventes
            if(PayPeriod(saleReceipt.getDate(), pc)){ // Si le reçu se trouve dans la période précédent la date de payement
                totalpay += CalculCommission(saleReceipt); // On calcule la part de l'employé et on l'ajoute au total
            }
        }
        pc.setPay(totalpay); // On envoie le total de sa paie.
        return;
    }

    @Override
    public String toString() {
        return "commissioned";
    }
}
