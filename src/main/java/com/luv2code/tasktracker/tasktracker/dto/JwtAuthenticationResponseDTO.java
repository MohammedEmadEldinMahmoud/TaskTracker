package com.luv2code.tasktracker.tasktracker.dto;

import jakarta.validation.constraints.NotBlank;

public class JwtAuthenticationResponseDTO {

    @NotBlank(message = "Token must not be blank")
    private String token;

    @NotBlank(message = "Refresh token must not be blank")
    private String refreshToken;

    public JwtAuthenticationResponseDTO() {
    }

    public JwtAuthenticationResponseDTO(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
