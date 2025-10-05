package com.luv2code.tasktracker.tasktracker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import com.luv2code.tasktracker.tasktracker.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

@JsonInclude(JsonInclude.Include.NON_NULL) // don’t include null fields in JSON
public class UserDTO {

    @Null(message = "Id must not be provided when creating a new user")
    private int id;
    @NotBlank(message = "Username is required")
    private String username;

    private Role role;


    // constructor
    public UserDTO(int id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
