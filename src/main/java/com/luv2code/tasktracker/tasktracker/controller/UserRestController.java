package com.luv2code.tasktracker.tasktracker.controller;

import com.luv2code.tasktracker.tasktracker.dto.UserDTO;
import com.luv2code.tasktracker.tasktracker.entity.User;
import com.luv2code.tasktracker.tasktracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    // add mapping for GET /users/{userId}
    @GetMapping("/users/{userId}")
    public UserDTO getUser(@PathVariable int userId){
        return userService.findById(userId);
    }

}
