package be.heh.epm.application.port.in;

import be.heh.epm.common.SelfValidating;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddEmployeeHourlyValidating extends SelfValidating<AddEmployeeHourlyValidating> {

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

    public AddEmployeeHourlyValidating(int empId, @NotNull @NotEmpty String name, @NotNull @NotEmpty String address, @NotNull @NotEmpty @Email String mail, @NotNull double hourlyRate) {
        this.empId = empId;
        this.name = name;
        this.address = address;
        this.mail = mail;
        this.hourlyRate = hourlyRate;
        this.validateSelf();
    }


// GETTERS & SETTERS

// METHODS
}
