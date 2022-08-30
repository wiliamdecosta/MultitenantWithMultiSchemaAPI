package com.justclick.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justclick.beans.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findByNik(String nik);

    void deleteByNik(String nik);
}

