package com.eventmanagement.Controller;

import com.eventmanagement.Dto.Response.MyEventResponse;
import com.eventmanagement.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;



    // ✅ Attendee dashboard API
    @GetMapping("/my-events")
    public List<MyEventResponse> getMyEvents(
            @RequestHeader("Authorization") String token
    ) {
        return orderService.getMyEvents(token);
    }
}