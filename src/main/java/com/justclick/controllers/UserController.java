package com.justclick.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.justclick.bean.User;
import com.justclick.service.UserService;

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

	@GetMapping("/all")
    public ResponseEntity<?> all() throws SQLException {
        List<User> listUser = service.getAll();
        return new ResponseEntity<>(listUser, HttpStatus.OK);
    }
}
