package com.example.demo;

import java.time.LocalDate;

public class Employee {
    private Integer id;
    private String name;
    private String role;
    private LocalDate dob;
    private String email;
    private String department;

    public Employee() {
    }

    public Employee(Integer id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Employee(Integer id, String name, String role, LocalDate dob, String email, String department) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.dob = dob;
        this.email = email;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
