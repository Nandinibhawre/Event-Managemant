package com.eventmanagement.Service;

import com.eventmanagement.Model.Event;
import com.eventmanagement.Model.Order;
import com.eventmanagement.Repository.EventRepository;
import com.eventmanagement.Repository.OrderRepository;
import com.eventmanagement.Security.jwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
