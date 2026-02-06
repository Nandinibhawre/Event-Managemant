package com.eventmanagement.Dto.Response;

import com.eventmanagement.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private Role role;
    public AuthResponse(String token) {
        this.token = token;
    }


}