package com.eventmanagement.Service;

import com.eventmanagement.Dto.Response.MyEventResponse;
import com.eventmanagement.Model.Event;
import com.eventmanagement.Model.Order;
import com.eventmanagement.Repository.EventRepository;
import com.eventmanagement.Repository.OrderRepository;
import com.eventmanagement.Security.jwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final EventRepository eventRepo;
    private final jwtUtil jwtUtil;

    public Order createOrder(String eventId, int quantity, String token) {

        String userId = jwtUtil.extractUserId(token.substring(7));

        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (event.getAvailableTickets() < quantity) {
            throw new RuntimeException("Not enough tickets");
        }

        // reduce tickets
        event.setAvailableTickets(
                event.getAvailableTickets() - quantity
        );
        eventRepo.save(event);

        Order order = new Order();
        order.setUserId(userId);
        order.setEventId(eventId);
        order.setQuantity(quantity);
        order.setTotalAmount(quantity * event.getTicketPrice());
        order.setCheckedIn(false);
        order.setOrderTime(LocalDateTime.now());

        // 🔥 Simple QR value
        order.setQrCode("QR-" + UUID.randomUUID());

        return orderRepo.save(order);
    }

    public List<MyEventResponse> getMyEvents(String token) {

        // ⭐⭐⭐ MOST IMPORTANT FIX ⭐⭐⭐
        // Remove "Bearer " before decoding JWT
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        // Extract userId from JWT
        String userId = jwtUtil.extractUserId(token);

        // Fetch orders of this user
        List<Order> orders = orderRepo.findByUserId(userId);

        List<MyEventResponse> responseList = new ArrayList<>();

        for (Order order : orders) {

            Event event = eventRepo.findById(order.getEventId())
                    .orElseThrow(() -> new RuntimeException("Event not found"));

            responseList.add(new MyEventResponse(
                    order.getOrderId(),
                    event.getTitle(),
                    event.getEventDate(),   // your start date field
                    order.isCheckedIn()
            ));
        }

        return responseList;
    }

}
