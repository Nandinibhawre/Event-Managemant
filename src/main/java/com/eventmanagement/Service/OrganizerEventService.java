package com.eventmanagement.Service;

import com.eventmanagement.Dto.Request.EventRequest;
import com.eventmanagement.Dto.Response.EventAnalyticsResponse;
import com.eventmanagement.Model.Booking;
import com.eventmanagement.Model.Event;

import com.eventmanagement.Model.Order;
import com.eventmanagement.Repository.BookingRepository;
import com.eventmanagement.Repository.EventRepository;
import com.eventmanagement.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizerEventService {

    private final EventRepository eventRepository;
    private final OrderRepository OrderRepository;

    // CREATE EVENT
    public void createEvent(EventRequest request, String organizerId) {

        Event event = new Event();

        event.setOrganizerId(organizerId);
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setEventDate(request.getEventDate());
        event.setTicketPrice(request.getTicketPrice());
        event.setTotalTickets(request.getTotalTickets());
        event.setAvailableTickets(request.getTotalTickets());
        event.setCreatedAt(LocalDateTime.now());

        eventRepository.save(event);
    }

    //Update events
//    public Event updateEvent(String eventId, EventRequest request) {
//
//        Event event = eventRepository.findById(eventId)
//                .orElseThrow(() -> new RuntimeException("Event not found"));
//
//        event.setTitle(request.getTitle());
//        event.setDescription(request.getDescription());
//        event.setLocation(request.getLocation());
//        event.setEventDate(request.getEventDate());
//        event.setTicketPrice(request.getTicketPrice());
//        event.setAvailableTickets(request.getTotalTickets());
//
//        return eventRepository.save(event);
//    }
    public Event updateEvent(String eventId, EventRequest request) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setLocation(request.getLocation());
        event.setEventDate(request.getEventDate());
        event.setTicketPrice(request.getTicketPrice());

        int oldTotal = event.getAvailableTickets();
        int newTotal = request.getTotalTickets();

        int oldAvailable = event.getAvailableTickets();


        // ✅ ADD / REMOVE only the difference
        int totalAvailable = newTotal + oldTotal;
        event.setAvailableTickets(totalAvailable);
        event.setTotalTickets(totalAvailable);

        return eventRepository.save(event);
    }

    // DELETE EVENT
    public void deleteEvent(String eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventRepository.delete(event);
    }

    public EventAnalyticsResponse getEventAnalytics(String eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        List<Order> orders = OrderRepository.findByEventId(eventId);

        int ticketsSold = orders.stream()
                .mapToInt(Order::getQuantity)
                .sum();

        double revenue = orders.stream()
                .mapToDouble(Order::getTotalAmount)
                .sum();

        return new EventAnalyticsResponse(
                eventId,
                event.getTotalTickets(),
                ticketsSold,
                event.getAvailableTickets(),
                revenue
        );
    }
}