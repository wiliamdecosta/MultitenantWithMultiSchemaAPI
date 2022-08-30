package com.justclick.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.justclick.beans.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
