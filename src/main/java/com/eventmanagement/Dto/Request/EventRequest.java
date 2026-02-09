package com.eventmanagement.Dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class EventRequest
{

    private String title;
    private String description;
    private String location;
    private LocalDateTime eventDate;
    private double ticketPrice;
    private int totalTickets;
//    private int availableTickets;
}
