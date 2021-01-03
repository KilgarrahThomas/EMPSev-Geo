package be.heh.epm.application.service;

import be.heh.epm.application.Context;
import be.heh.epm.application.logger.Logger;

public class DeleteEmploye implements Command {

    // ATTRIBUTES
    int id;

    // CONSTRUCTORS
    public DeleteEmploye(int id) {
        this.id = id;
        Logger.LogInfo(this.getClass().getName(), "asking for deleting employee : " + id);
    }

    // GETTERS & SETTERS

    // METHODS
    @Override
    // Supprime un employé selon son ID
    public void execute() {
        Context.emp.deleteEmployee(id);
    }
}
