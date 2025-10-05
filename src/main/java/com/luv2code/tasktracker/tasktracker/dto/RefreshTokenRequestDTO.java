package com.luv2code.tasktracker.tasktracker.dto;
import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequestDTO {

    @NotBlank(message = "Refresh token must not be blank")
    private String token;


    public RefreshTokenRequestDTO() {
    }

    public RefreshTokenRequestDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
