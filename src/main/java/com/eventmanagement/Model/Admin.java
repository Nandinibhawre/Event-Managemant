package com.eventmanagement.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "admins")
@Data
public class Admin {

    @Id
    private String adminid;

    private String name;
    private String email;
    private String password;
    private LocalDateTime createdAt;
}