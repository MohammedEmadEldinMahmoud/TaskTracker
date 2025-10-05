package com.luv2code.tasktracker.tasktracker.service;

import com.luv2code.tasktracker.tasktracker.dto.JwtAuthenticationResponseDTO;
import com.luv2code.tasktracker.tasktracker.dto.LoginRequestDTO;
import com.luv2code.tasktracker.tasktracker.dto.RefreshTokenRequestDTO;
import com.luv2code.tasktracker.tasktracker.dto.RegisterRequestDTO;
import com.luv2code.tasktracker.tasktracker.entity.Role;
import com.luv2code.tasktracker.tasktracker.entity.User;
import com.luv2code.tasktracker.tasktracker.enums.RoleName;
import com.luv2code.tasktracker.tasktracker.repositories.RoleRepository;
import com.luv2code.tasktracker.tasktracker.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private  RoleRepository roleRepository;

    private AuthenticationManager authenticationManager;

    private JwtService jwtService;

    public AuthenticationService(UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 RoleRepository roleRepository,
                                 AuthenticationManager authenticationManager,
                                 JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequestDTO registerRequestDTO){
        User user = new User();
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setEmail(registerRequestDTO.getEmail());
        String name = registerRequestDTO.getFirstName() + " " + registerRequestDTO.getLastName();
        user.setName(name);

        Role role = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new RuntimeException("Role USER not found in DB"));
        user.setRole(role);

        return userRepository.save(user);
    }

    public JwtAuthenticationResponseDTO login(LoginRequestDTO loginRequestDTO){
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(), loginRequestDTO.getPassword()));

       var user = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
       var jwt = jwtService.generateToken(user);
       var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);
       JwtAuthenticationResponseDTO  jwtAuthenticationResponseDTO = new JwtAuthenticationResponseDTO();
        jwtAuthenticationResponseDTO.setToken(jwt);
        jwtAuthenticationResponseDTO.setRefreshToken(refreshToken);
        return jwtAuthenticationResponseDTO;

    }


    public JwtAuthenticationResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO){
      String userEmail = jwtService.extractUserName(refreshTokenRequestDTO.getToken());
      User user = userRepository.findByEmail(userEmail).orElseThrow();

      if(jwtService.isTokenValid(refreshTokenRequestDTO.getToken(), user)){

          var jwt = jwtService.generateToken(user);

          JwtAuthenticationResponseDTO  jwtAuthenticationResponseDTO = new JwtAuthenticationResponseDTO();
          jwtAuthenticationResponseDTO.setToken(jwt);
          jwtAuthenticationResponseDTO.setRefreshToken(refreshTokenRequestDTO.getToken());
          return jwtAuthenticationResponseDTO;
          }
      return null;
      }









    }


