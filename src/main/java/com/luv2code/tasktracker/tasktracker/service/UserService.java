package com.luv2code.tasktracker.tasktracker.service;

import com.luv2code.tasktracker.tasktracker.dto.UserDTO;
import com.luv2code.tasktracker.tasktracker.entity.User;
import com.luv2code.tasktracker.tasktracker.enums.RoleName;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions.LogicException;
import com.luv2code.tasktracker.tasktracker.exceptionhandlers.exceptions.UserNotFoundException;
import com.luv2code.tasktracker.tasktracker.mapper.UserMapper;
import com.luv2code.tasktracker.tasktracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {


    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDTO findById(int theId) {
        // get authenticated user from SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User authUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Authenticated user not found"));

        // if role = USER → allow only self
        if (authUser.getRole().getName() == RoleName.USER && authUser.getId() != theId) {
            throw new LogicException("Users can only access their own profile");
        }

        // otherwise (ADMIN or same user), fetch normally
        Optional <User> result = userRepository.findById(theId);
        User theuser = null;
        if(result.isPresent()){
            theuser = result.get();
        }
        else {
            throw new UserNotFoundException("Did not find user id of "+ theId);
        }
        return UserMapper.toDTO(theuser);
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User not found"));
            }
        };
    }


    }
    
    



