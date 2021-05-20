package com.service;


import com.config.RabbitMQConfig;
import com.model.Event;
import com.model.Role;
import com.model.User;
import com.repository.EventRepository;
import com.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class EventManagerService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;
    @Autowired
    RabbitTemplate rabbitTemplate;

    MessageListenerAdapter listenerAdapter;
    @Autowired
    MongoTemplate mongoTemplate;

    // code for editing rabbitmq template and settings routing key, queue
//    @Autowired
//    private RabbitMQService rabbitMQService;
    @Autowired
    private RabbitMQConfig rabbitMQConfig;


    public void register(String name) {

        if (userRepository.findByName(name).isPresent()) {
            log.info("This user already registered");
        } else {
            mongoTemplate.insert(new User(name, String.valueOf(Role.PUBLISHER)), "users");
        }
    }

    public List<Event> subscribe(String publisherName) {
        //need to create queue
        Optional<User> optionalUser = userRepository.findByName(publisherName);
        ArrayList<Event> optionalEvent = eventRepository.findByPublisher(publisherName);
        if (optionalUser.isPresent()) {
            if (!optionalEvent.isEmpty()) {
                return new ArrayList<>(optionalEvent);
            }
        }
        return null;
    }

    public void receive() {

    }

    public boolean sendPost(String message, String publisherName) {
    //message = null
        if (isPublisherExists(publisherName)) {
//            rabbitMQService.sendMessage(message);
            saveMessageInDatabase(message, publisherName);
            rabbitTemplate.convertAndSend(rabbitMQConfig.getDefaultExchange() + publisherName, rabbitMQConfig.getDefaultRoutingKey(), message);
            return true;
        }
        return false;
    }

    public void saveMessageInDatabase(String message, String publisherName) {

        Date in = new Date();
        LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(), ZoneId.systemDefault());
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        Document document = new Document();
        document.append("message", message).append("date", out).append("publisher", publisherName);

        mongoTemplate.insert(document, "events");
    }

    public List<User> showAllPublishers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));

    }


    public boolean isPublisherExists(String name) {

        return userRepository.findByName(name).isPresent();
    }
}
