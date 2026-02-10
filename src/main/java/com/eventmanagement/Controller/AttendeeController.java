package com.eventmanagement.Controller;

import com.eventmanagement.Model.Event;
import com.eventmanagement.Model.Order;
import com.eventmanagement.Service.EventService;
import com.eventmanagement.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class AttendeeController {

    private final EventService eventService;
    private final OrderService orderService;

    // 1️⃣ LIST EVENTS
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // 2️⃣ EVENT DETAILS
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable String id) {
        return eventService.getEventById(id);
    }

    // 3️⃣ BUY TICKET
    @PostMapping("/{id}/orders")
    public Order buyTicket(
            @PathVariable String id,
            @RequestParam int quantity,
            @RequestHeader("Authorization") String token
    ) {
        return orderService.createOrder(id, quantity, token);
    }
}


