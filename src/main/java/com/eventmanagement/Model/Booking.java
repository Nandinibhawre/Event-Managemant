package com.eventmanagement.Model;

import com.eventmanagement.Enum.BookingStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "bookings")
public class Booking {

    @Id
    private String bookingId;

    // 🔗 References
    private String eventId;
    private String organizerId;
    private String attendeeId;

    // 🎫 Ticket info
    private int ticketsBooked;
    private double pricePerTicket;
    private double totalAmount;

    // 📊 Status (important later)
    private BookingStatus status;   // CONFIRMED, CANCELLED

    // ⏱️ Audit
    private LocalDateTime bookedAt;
    private LocalDateTime cancelledAt;
}
