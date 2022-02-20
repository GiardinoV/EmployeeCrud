package com.springboot2.springboot.Controller;


import com.springboot2.springboot.Models.Employee;
import com.springboot2.springboot.Repositories.IEmployeeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class EmployeeController {

    @Autowired
    IEmployeeJpaRepository employeeJpaRepository;

    //Obtener todos los empleados
    @GetMapping("/")
    public ResponseEntity<List<Employee>> getEmployees(){
        try {
            List<Employee> employee1 = new ArrayList<Employee>();
            employeeJpaRepository.findAll().forEach(employee1::add);
            if (employee1.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employee1, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Obtener empleados por id
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long id){
        Optional<Employee> employeeData = employeeJpaRepository.findById(id);
        if (employeeData.isPresent()) {
            return new ResponseEntity<>(employeeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Creo un nuevo empleado
    @PostMapping("/add")
    public ResponseEntity<Employee> addNewEmployee(@RequestBody Employee employee){
        try{
            Employee employee1 = employeeJpaRepository.save(new Employee(employee.getFirstName(),
                    employee.getLastName(), employee.getEmployeeid(), employee.getRole()));
            return new ResponseEntity<>(employee1, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    //Actualizar datos de empleado
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee) {
        Optional<Employee> employee1 = employeeJpaRepository.findById(id);

        if (employee1.isPresent()) {
            Employee employee_ = employee1.get();
            employee_.setFirstName(employee.getFirstName());
            employee_.setLastName(employee.getLastName());
            employee_.setEmployeeid(employee.getEmployeeid());
            employee_.setRole(employee.getRole());
            // employee_.setProjects(employee.getProjects());
            return new ResponseEntity<>(employeeJpaRepository.save(employee_), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar empleado por id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
        try {
            employeeJpaRepository.deleteById(id);
            return new ResponseEntity<>("Employee delete",HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

}
