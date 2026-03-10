package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class EmployeeDAO {
    private final Employees employees = new Employees();

    public EmployeeDAO() {
        employees.getEmployeeList().add(new Employee(1, "Alice", "Developer"));
        employees.getEmployeeList().add(new Employee(2, "Bob", "QA"));
    }

    public Employees getAllEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.getEmployeeList().add(employee);
    }
// DELETE method to delete an employee
    public void deleteEmployee(Integer id) {
        employees.getEmployeeList().removeIf(employee -> employee.getId().equals(id));
    }
}
