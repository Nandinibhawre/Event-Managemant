package com.eventmanagement.Repository;

import com.eventmanagement.Model.Event;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String>
{
    List<Event> findByOrganizerId(String organizerId);
}
