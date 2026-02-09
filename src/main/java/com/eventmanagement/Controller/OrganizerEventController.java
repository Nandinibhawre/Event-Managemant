package com.eventmanagement.Controller;


import com.eventmanagement.Dto.Request.EventRequest;
import com.eventmanagement.Dto.Response.EventAnalyticsResponse;
import com.eventmanagement.Model.Event;
import com.eventmanagement.Service.OrganizerEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/organizer")
@RequiredArgsConstructor
public class OrganizerEventController {

    private final OrganizerEventService organizerEventService;

    // CREATE EVENT
    @PostMapping("/events")
    public ResponseEntity<String> createEvent(@RequestBody EventRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String organizerId = authentication.getName(); // login id

        organizerEventService.createEvent(request, organizerId);

        return ResponseEntity.ok("Event created successfully");


    }


    // UPDATE EVENT
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(
            @PathVariable String id,
            @RequestBody EventRequest request) {

        return ResponseEntity.ok(
                organizerEventService.updateEvent(id, request)
        );
    }

        // DELETE EVENT
        @DeleteMapping("/{id}")
        public ResponseEntity<String> deleteEvent(@PathVariable String id) {

            organizerEventService.deleteEvent(id);
            return ResponseEntity.ok("Event deleted successfully");
        }

        // EVENT ANALYTICS
        @GetMapping("/{id}/analytics")
        public ResponseEntity<EventAnalyticsResponse> getAnalytics(
                @PathVariable String id) {

            return ResponseEntity.ok(
                    organizerEventService.getEventAnalytics(id)
            );
        }
    }

