package com.justclick.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justclick.beans.User;
import com.justclick.inputs.CreateUserAndTenantInput;
import com.justclick.outputs.CreateUserAndTenantOutput;
import com.justclick.services.UserService;
import com.justclick.services.UserTenantService;

/**
 * 
 * User controller TIDAK membutuhkan header X-TenantID
 *
 */

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserTenantService utService;

	@GetMapping("/all")
    public ResponseEntity<?> all() throws SQLException {
        List<User> listUser = service.getAll();
        return new ResponseEntity<>(listUser, HttpStatus.OK);
    }
	
	@PostMapping("/create")
	public ResponseEntity<?> createUserAndTenant(@RequestBody CreateUserAndTenantInput userTenantInput) throws SQLException {
        CreateUserAndTenantOutput output = utService.callCreateSchemaProcedure(userTenantInput);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
	
}
