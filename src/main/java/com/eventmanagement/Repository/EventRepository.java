package com.eventmanagement.Repository;

import com.eventmanagement.Model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
