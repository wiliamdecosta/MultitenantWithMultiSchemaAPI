package com.justclick.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justclick.beans.Employee;
import com.justclick.services.EmployeeService;


/**
 * 
 * Employee controller membutuhkan header X-TenantID
 *
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService service;
	
	@PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody Employee employee) {
        service.save(employee);
        return new ResponseEntity<>(HttpStatus.OK);
    }
	
	@GetMapping("/all")
    public ResponseEntity<?> all() throws SQLException {
        List<Employee> listEmployee = service.getAll();
        return new ResponseEntity<>(listEmployee, HttpStatus.OK);
    }
	
	@GetMapping("/{nik}") 
	public ResponseEntity<?> getByNik(@PathVariable(value = "nik") String nik) {
		Employee employee = service.getByNik(nik);
		 return new ResponseEntity<>(employee, HttpStatus.OK);
	}

}
