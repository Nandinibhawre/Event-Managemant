package com.eventmanagement.Repository;

import com.eventmanagement.Model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface OrderRepository extends MongoRepository<Order, String> {
        List<Order> findByEventId(String eventId);
        List<Order> findByUserId(String userId);
    }

