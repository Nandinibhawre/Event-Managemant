package com.eventmanagement.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class EventAnalyticsResponse {

    private String eventId;
    private int totalTickets;
    private int ticketsSold;
    private int remainingTickets;
    private double totalRevenue;
}