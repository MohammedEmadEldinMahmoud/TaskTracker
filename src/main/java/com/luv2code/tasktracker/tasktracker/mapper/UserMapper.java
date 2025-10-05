package com.luv2code.tasktracker.tasktracker.mapper;

import com.luv2code.tasktracker.tasktracker.dto.UserDTO;
import com.luv2code.tasktracker.tasktracker.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user){
        return new UserDTO(user.getId(), user.getName(), user.getRole());
    }
}
