package be.heh.epm.adapter.web;

import be.heh.epm.application.port.in.*;
import be.heh.epm.application.port.out.EmployeePort;
import be.heh.epm.common.WebAdapter;
import be.heh.epm.domain.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;

//@Api(tags = "Employees Management")
@WebAdapter
@RestController
public class EmployeeController {

    @Autowired
    @Qualifier("Salaried")
    private AddEmployeeUseCase addSalariedEmployee;
    @Autowired
    @Qualifier("Hourly")
    private AddEmployeeUseCase addHourlyEmployee;
    private GetAllEmployeesUserCase getAllEmployees;
    private GetEmployeeUserCase getEmployee;
    private DeleteEmployeeUserCase deleteEmployee;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);


    public EmployeeController(@Qualifier("Salaried") AddEmployeeUseCase addSalariedEmployee,
                              @Qualifier("Hourly") AddEmployeeUseCase addHourlyEmployee, GetAllEmployeesUserCase getAllEmployees,
                              GetEmployeeUserCase getEmployee, DeleteEmployeeUserCase deleteEmployee){
        this.addSalariedEmployee = addSalariedEmployee;
        this.addHourlyEmployee = addHourlyEmployee;
        this.getAllEmployees = getAllEmployees;
        this.getEmployee = getEmployee;
        this.deleteEmployee = deleteEmployee;
    }

    @GetMapping("/helloworld") //API de test, retourne un Hello World
    public ResponseEntity helloworld() {
        logger.info("Test Connexion API");
        return ResponseEntity.ok("Hello World :P");
    }

    @CrossOrigin
    @PostMapping("/employees/salaried")
//    @ApiOperation(value = "Create new Employee")
    ResponseEntity<Void> newEmployee(@Valid @RequestBody EmployeeSalariedValidating newEmployee) {

        logger.info("Creating new employee with name: " + newEmployee.getName() +", address: " + newEmployee.getAddress());

        addSalariedEmployee.execute(newEmployee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee.getEmpId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @CrossOrigin
    @PostMapping("/employees/hourlied")
//    @ApiOperation(value = "Create new Employee")
    ResponseEntity<Void> newEmployee(@Valid @RequestBody EmployeeHourlyValidating newEmployee) {

        logger.info("Creating new employee with name: " + newEmployee.getName() +", address: " + newEmployee.getAddress());

        addHourlyEmployee.execute(newEmployee);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newEmployee.getEmpId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/employees")
    @CrossOrigin
//    @ApiOperation(value = "Get All Employees")
    ResponseEntity<ArrayList<Employee>> GetAllEmployee() {
        logger.info("Getting all employees");

        return ResponseEntity.ok(getAllEmployees.execute());
    }

    @GetMapping("/employees/{id}")
    @CrossOrigin
//    @ApiOperation(value = "Get Employees")
    ResponseEntity<Employee> GetEmployeeById(@PathVariable final Integer id) {

        logger.info("Getting employee n°" + id);

        return ResponseEntity.ok(getEmployee.execute(id));
    }

    @DeleteMapping("/employees/{id}")
    @CrossOrigin
//    @ApiOperation(value = "Delete Employee")
    ResponseEntity<Void> DeleteEmployeeById(@PathVariable final Integer id) {

        logger.info("Deleting employee n°" + id);

        deleteEmployee.execute(id);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/employees)")
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
