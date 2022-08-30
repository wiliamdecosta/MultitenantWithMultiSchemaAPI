package com.justclick.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justclick.beans.Employee;
import com.justclick.repositories.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepo;

    public void save(Employee employee) {
        employeeRepo.save(employee);
    }

    public List<Employee> getAll() throws SQLException {
        return employeeRepo.findAll();
    }

    public Employee get(Integer id) {
        return employeeRepo.findById(id).get();
    }

    public Employee getByNik(String nik) {
        return employeeRepo.findByNik(nik);
    }

    public void delete(String nik) {
        employeeRepo.deleteByNik(nik);
    }
}

