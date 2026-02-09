package com.eventmanagement.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "organizers")
@Data
public class Organizer {

    @Id
    private String organizerId;

    private String name;
    private String email;
    private String password;

    private String companyName;
    private LocalDateTime createdAt;
}
