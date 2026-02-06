package com.eventmanagement.Model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.eventmanagement.Enum.Role;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String fullName;
    private String email;
    private String password;

    private Role role; // ADMIN, ORGANIZER, ATTENDEE

    private String profileImage;
    private LocalDateTime createdAt;
}