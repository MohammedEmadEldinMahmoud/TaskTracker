package com.luv2code.tasktracker.tasktracker.controller;

import com.luv2code.tasktracker.tasktracker.dto.*;
import com.luv2code.tasktracker.tasktracker.entity.User;
import com.luv2code.tasktracker.tasktracker.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private AuthenticationService authenticationService;

    public AuthRestController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserDTO register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return authenticationService.register(registerRequestDTO);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return authenticationService.login(loginRequestDTO);
    }


    @PostMapping("/refresh")
    public JwtAuthenticationResponseDTO refresh(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return authenticationService.refreshToken(refreshTokenRequestDTO);
    }
}
