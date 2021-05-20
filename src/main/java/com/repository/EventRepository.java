package com.repository;

import com.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;


public interface EventRepository  extends MongoRepository<Event, Integer> {

    ArrayList<Event> findByPublisher(String publisher);
}
