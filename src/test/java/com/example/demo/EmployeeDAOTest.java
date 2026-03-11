package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmployeeDAOTest {

    @Test
    void addEmployee_increasesListSizeAndStoresData() {
        EmployeeDAO dao = new EmployeeDAO();
        int originalSize = dao.getAllEmployees().getEmployeeList().size();

        Employee newEmployee = new Employee(null, "Charlie", "Manager");
        dao.addEmployee(newEmployee);

        int newSize = dao.getAllEmployees().getEmployeeList().size();
        assertEquals(originalSize + 1, newSize, "List size should increase by 1 after addEmployee");

        assertTrue(
                dao.getAllEmployees().getEmployeeList().stream()
                        .anyMatch(e -> "Charlie".equals(e.getName()) && "Manager".equals(e.getRole())),
                "New employee should be present in the list with correct data"
        );
    }

    @Test
    void deleteEmployee_removesEmployeeWithGivenId() {
        EmployeeDAO dao = new EmployeeDAO();

        int originalSize = dao.getAllEmployees().getEmployeeList().size();

        dao.deleteEmployee(1);

        int newSize = dao.getAllEmployees().getEmployeeList().size();
        assertEquals(originalSize - 1, newSize, "List size should decrease by 1 after deleteEmployee");

        assertTrue(
                dao.getAllEmployees().getEmployeeList().stream()
                        .noneMatch(e -> Integer.valueOf(1).equals(e.getId())),
                "Employee with id=1 should no longer be present"
        );
    }
}

