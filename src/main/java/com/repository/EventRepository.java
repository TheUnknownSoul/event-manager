package com.repository;

import com.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

//public interface EventRepository  extends JpaRepository<Event, Integer> {
public interface EventRepository  extends MongoRepository<Event, Integer> {

    Optional<Event> findByEventId(Event event);
}
