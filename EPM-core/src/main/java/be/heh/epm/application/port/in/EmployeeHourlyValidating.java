package be.heh.epm.application.port.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class EmployeeHourlyValidating extends EmployeeValidating{

// ATTRIBUTES
    @Getter
    @Setter
    private int empId;
    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String name;
    @NotNull
    @NotEmpty
    @Getter
    @Setter
    private String address;
    @NotNull
    @NotEmpty
    @Email
    @Getter
    @Setter
    private String mail;
    @NotNull
    @Getter
    @Setter
    private double hourlyRate;

// CONSTRUCTOR

// GETTERS & SETTERS

// METHODS
}
