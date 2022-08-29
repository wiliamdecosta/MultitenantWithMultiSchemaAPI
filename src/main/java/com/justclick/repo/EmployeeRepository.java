package com.justclick.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justclick.bean.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findByNik(String nik);

    void deleteByNik(String nik);
}

