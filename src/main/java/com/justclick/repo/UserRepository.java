package com.justclick.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justclick.bean.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
