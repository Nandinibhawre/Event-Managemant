package com.eventmanagement.Controller;

import com.eventmanagement.Model.Order;
import com.eventmanagement.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class CheckInController {

    private final OrderRepository orderRepo;

    // 4️⃣ CHECK-IN
    @PostMapping("/{id}/checkin")
    public String checkIn(@PathVariable String id) {

        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.isCheckedIn()) {
            return "Already checked in";
        }

        order.setCheckedIn(true);
        orderRepo.save(order);

        return "Check-in successful";
    }
}
