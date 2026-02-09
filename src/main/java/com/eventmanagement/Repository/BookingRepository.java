package com.eventmanagement.Repository;

import com.eventmanagement.Enum.BookingStatus;
import com.eventmanagement.Model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {

    // Organizer analytics
    List<Booking> findByEventIdAndStatus(String eventId, BookingStatus status);

    // Organizer dashboard (all bookings of organizer)
    List<Booking> findByOrganizerIdAndStatus(String organizerId, BookingStatus status);

    // Attendee booking history
    List<Booking> findByAttendeeId(String attendeeId);
}