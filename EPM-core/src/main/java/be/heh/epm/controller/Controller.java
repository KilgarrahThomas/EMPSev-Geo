package be.heh.epm.controller;

import be.heh.epm.domain.employee.Employee;
import be.heh.epm.database.DataBaseHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController //Defini cette classe comme un controlleur
@CrossOrigin(origins = "http://localhost:3000") //Permet de lier le backend et le frontend

public class Controller {
    DataBaseHelper db = new DataBaseHelper();

    @GetMapping("/api/helloworld") //API de test, retourne un Hello World
    public ResponseEntity helloworld() {
        return ResponseEntity.ok("Hello World :P");
    }

    @GetMapping("/api/employee") //API all Employee: utilisera la methode getAllEmployee de DBhelper
    public Map<Integer, Employee> getAllEmployees() {
        return db.getAllEmployees();
    }

    @GetMapping("/api/employee/{id}") //API ID Employee: utilisera la methode getEmployee de DBhelper
    public Employee findByid(@PathVariable int id) {
        Employee emp = db.getEmployee(id);
        if(emp==null)
            throw new UserNotFoundException("id-" + id + " not found !");
        return emp;
    }

    @PostMapping("/api/create") //API Create Employee: prend un json en requestbody pour inserer un employe dans la db
    public ResponseEntity parametrage(@RequestBody Employee requestedEmployee) {
        System.out.println(requestedEmployee.getEmpID());
        db.save(requestedEmployee.getEmpID(), requestedEmployee);
        return ResponseEntity.ok(requestedEmployee.getName());
    }

    @DeleteMapping("/api/employee/delete") //API delete Employee: prend un id en parametre pour supprimer un employe dans la db
    public ResponseEntity deleteClient(@RequestParam(name = "id") int id) {
        db.deleteEmployee(id);
        return ResponseEntity.ok("DELETE");
    }
}