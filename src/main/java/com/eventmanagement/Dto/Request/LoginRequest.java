package com.eventmanagement.Dto.Request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
    private String role;
}