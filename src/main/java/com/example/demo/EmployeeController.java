package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

// REST Controller for managing employees
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeDAO employeeDao;

    public EmployeeController(EmployeeDAO employeeDao) {
        this.employeeDao = employeeDao;
    }

    // GET endpoint to fetch all employees
    @GetMapping("/")
    public Employees getEmployees() {
        return employeeDao.getAllEmployees();
    }

    // POST endpoint to add a new employee
    @PostMapping("/")
    public ResponseEntity<Object> 
      addEmployee(@RequestBody Employee employee) {
      
        // Generate ID for the new employee
        Integer id = employeeDao.getAllEmployees()
          .getEmployeeList().size() + 1;
        employee.setId(id);

        // Add employee to the list
        employeeDao.addEmployee(employee);

        // Build location URI for the new employee
        URI location = ServletUriComponentsBuilder
          .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employee.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
    // DELETE endpoint to delete an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Integer id) {
        employeeDao.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
