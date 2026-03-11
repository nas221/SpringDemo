package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class EmployeeDAOTest {

    @Autowired
    private EmployeeDAO dao;

    @Test
    void addEmployee_increasesRowCountAndStoresData() {
        List<Employee> before = dao.getAllEmployees();
        int originalSize = before.size();

        Employee newEmployee = new Employee(null, "Charlie", "Manager");
        dao.addEmployee(newEmployee);

        List<Employee> after = dao.getAllEmployees();
        int newSize = after.size();
        assertEquals(originalSize + 1, newSize, "Row count should increase by 1 after addEmployee");

        assertTrue(
                after.stream()
                        .anyMatch(e -> "Charlie".equals(e.getName()) && "Manager".equals(e.getRole())),
                "New employee should be present in the table with correct data"
        );
    }

    @Test
    void deleteEmployee_removesEmployeeWithGivenId() {
        List<Employee> before = dao.getAllEmployees();
        int originalSize = before.size();

        dao.deleteEmployee(1);

        List<Employee> after = dao.getAllEmployees();
        int newSize = after.size();
        assertEquals(originalSize - 1, newSize, "Row count should decrease by 1 after deleteEmployee");

        assertTrue(
                after.stream()
                        .noneMatch(e -> Integer.valueOf(1).equals(e.getId())),
                "Employee with id=1 should no longer be present in the table"
        );
    }
}

