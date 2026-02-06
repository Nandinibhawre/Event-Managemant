package com.eventmanagement.Dto.Request;



import com.eventmanagement.Enum.Role;

import lombok.Data;

@Data
public class RegisterRequest {
	 private String fullName;
	  private String email;
	 private String password;
	    private Role role;
}
