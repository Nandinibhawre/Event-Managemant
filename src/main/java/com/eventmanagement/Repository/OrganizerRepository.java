package com.eventmanagement.Repository;

import com.eventmanagement.Model.Organizer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OrganizerRepository extends MongoRepository<Organizer, String> {
    Optional<Organizer> findByEmail(String email);
}