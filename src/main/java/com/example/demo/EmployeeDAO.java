package com.example.demo;

import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
@Component
public class EmployeeDAO {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Employee> rowMapper = (rs, rowNum) ->
            new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("role"),
                    rs.getObject("dob", LocalDate.class),
                    rs.getString("email"),
                    rs.getString("department")
            );

    public EmployeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Employee> getAllEmployees() {
        String sql = "SELECT id, name, role, dob, email, department FROM employee ORDER BY id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Employee addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (name, role, dob, email, department) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getRole());
            ps.setDate(3, employee.getDob() == null ? null : Date.valueOf(employee.getDob()));
            ps.setString(4, employee.getEmail());
            ps.setString(5, employee.getDepartment());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            employee.setId(key.intValue());
        }
        return employee;
    }

    public void deleteEmployee(Integer id) {
        String sql = "DELETE FROM employee WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Optional<Employee> findByID(Integer id) {
        String sql = "SELECT id, name, role, dob, email, department FROM employee WHERE id = ?";
        List<Employee> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.stream().findFirst();
    }

    public int updateEmployee(Integer id, Employee employee) {
        String sql = "UPDATE employee SET name = ?, role = ?, dob = ?, email = ?, department = ? WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                employee.getName(),
                employee.getRole(),
                employee.getDob() == null ? null : Date.valueOf(employee.getDob()),
                employee.getEmail(),
                employee.getDepartment(),
                id
        );
    }
}
