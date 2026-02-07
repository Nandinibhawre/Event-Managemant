package com.eventmanagement.Model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Document(collection = "users")
public class User
{
        @Id
        private String userid;

        private String name;
        private String email;
        private String password;
        private LocalDateTime createdAt;
}