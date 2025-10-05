package com.luv2code.tasktracker.tasktracker.repositories;

import com.luv2code.tasktracker.tasktracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Integer> {


    Optional<User> findByEmail(String Email);
}
