package com.justclick.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justclick.beans.User;
import com.justclick.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;

	public List<User> getAll() throws SQLException {
		return userRepo.findAll();
    }
}
