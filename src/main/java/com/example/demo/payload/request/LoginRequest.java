package com.example.demo.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "Username can not be Empty")
    private String username;
    @NotEmpty(message = "Password can not be Empty")
    private String password;
}
