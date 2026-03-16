package com.example.demo;

import java.sql.PreparedStatement;
import java.sql.Statement;
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
                    rs.getString("role")
            );

    public EmployeeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Employee> getAllEmployees() {
        String sql = "SELECT id, name, role FROM employee ORDER BY id";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public Employee addEmployee(Employee employee) {
        String sql = "INSERT INTO employee (name, role) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getRole());
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
        String sql = "SELECT id, name, role FROM employee WHERE id = ?";
        List<Employee> results = jdbcTemplate.query(sql, rowMapper, id);
        return results.stream().findFirst();
    }

    public int updateEmployee(Integer id, Employee employee) {
        String sql = "UPDATE employee SET name = ?, role = ? WHERE id = ?";
        return jdbcTemplate.update(sql, employee.getName(), employee.getRole(), id);
    }
}
