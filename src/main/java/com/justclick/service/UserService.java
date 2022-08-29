package com.justclick.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.justclick.bean.User;
import com.justclick.repo.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;

	public List<User> getAll() throws SQLException {
		return userRepo.findAll();
    }
}
