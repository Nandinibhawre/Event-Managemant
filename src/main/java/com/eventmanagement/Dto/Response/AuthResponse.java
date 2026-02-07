package com.eventmanagement.Dto.Response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AuthResponse
{
    private String token;
    private String email;
    private String role;

    public AuthResponse(String token, String email, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

    // getters
    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}


