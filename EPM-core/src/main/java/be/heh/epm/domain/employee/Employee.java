package be.heh.epm.domain.employee;

import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.payMethod.PaymentMethod;
import be.heh.epm.domain.schedule.PaymentSchedule;
import be.heh.epm.domain.payDay.PayCheck;

import java.time.LocalDate;

public class Employee {

    // ATTRIBUTES
    private int EmpID;
    private String name;
    private String address;
    private String mail;
    private PaymentMethod transaction;
    private PaymentSchedule payDay;
    private PaymentClassification payClassification;

    // CONSTRUCTOR
    public Employee(int id, String name, String address, String mail) {
        this.EmpID = id;
        this.name = name;
        this.address = address;
        this.mail = mail;
    }

    //Constructeur utiliser par la DB
    public Employee(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public Employee() {
    }

    // GETTERS & SETTERS
    public void setEmpId(int idemp) { this.EmpID = idemp; }

    public int getEmpID() {
        return this.EmpID;
    }
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PaymentMethod getPayMethod() {
        return transaction;
    }

    public void setPayMethod(PaymentMethod transaction) {
        this.transaction = transaction;
    }
    public PaymentSchedule getPaySchedule() {
        return payDay;
    }

    public void setPaySchedule(PaymentSchedule payDay) {
        this.payDay = payDay;
    }

    public PaymentClassification getPayClassification() {
        return payClassification;
    }

    public void setPayClassification(PaymentClassification payClassification) {
        this.payClassification = payClassification;
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    // METHODS
    public void payDay(PayCheck pc) {
        if(payDay.IsDatePay(pc.getDate())) { // Vérifie si l'employé doit bien être payé en ce jour
            pc.setPayPayPeriodStart(payDay.GetStartPayPeriod(pc.getDate())); // Défini la date du début de la période de payement
            pc.setMethod(this.transaction.toString()); // Définit la méthode de payement
            payClassification.CalculationSalary(pc); // Calcule le salaire reçu par l'employé
        }
        return;
    }

    public boolean isDatePay(LocalDate date) {
        return payDay.IsDatePay(date);
    } // Retourne true si l'employé doit être payé à la date définie

}
