package be.heh.epm.application.service;

import be.heh.epm.application.Context;
import be.heh.epm.application.logger.Logger;
import be.heh.epm.domain.employee.Employee;
import be.heh.epm.domain.payMethod.MailMethod;
import be.heh.epm.domain.classification.PaymentClassification;
import be.heh.epm.domain.schedule.PaymentSchedule;

public abstract class AddEmployee implements Command {

    // ATTRIBUTES
    int id;
    private String name;
    private String address;
    private String mail;
    protected Employee employee;

    // CONSTRUCTOR
    public AddEmployee(int id, String name, String address, String mail) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.mail = mail;
        employee = new Employee(id, name, address, mail);
    }

    // GETTERS & SETTERS

    // METHODS
    public abstract PaymentClassification makePayClassification(); // Définit la Classification

    public abstract PaymentSchedule makePaySchedule(); // Défini le schéma de payement

    @Override
    public void execute() {
        //Employé créé à la construction de la classe
        employee.setPayClassification(makePayClassification()); // Définition de la Classification de l'employé selon la classe appellée
        employee.setPaySchedule(makePaySchedule()); // Définition du Schéma de Payement de l'employé selon la classe appellée
        employee.setPayMethod(new MailMethod(employee.getMail())); // Définit la méthode de payement sur Mail par Défaut
        Logger.LogInfo(this.getClass().getName(), "Ask to write in DataBase");
        Context.emp.createEmployee(employee); // Envoie l'employé pour la sauvegarde sur la BDD

    }
}
