package com.eventmanagement.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "events")
public class Event {

    @Id
    private String eventId;

    private String title;
    private String description;
    private String location;
    private LocalDateTime eventDate;

    private double ticketPrice;
    private int totalTickets;
    private int availableTickets;

    private String organizerId;

    private LocalDateTime createdAt;
}
