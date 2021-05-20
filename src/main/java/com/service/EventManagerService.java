package com.service;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class EventManagerService {

    @Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    RabbitTemplate rabbitTemplate;
    MessageListenerAdapter listenerAdapter;
    @Value("${spring.rabbitmq.template.exchange}")
    private String defaultExchange;

    public void register(String name) {
        TopicExchange topicExchange = new TopicExchange(defaultExchange + name);
        amqpAdmin.declareExchange(topicExchange);
    }

//    public List<Event> subscribe(String publisherName) {
//        //need to create queue
//        Optional<User> optionalUser = userRepository.findByName(publisherName);
//        ArrayList<Event> optionalEvent = eventRepository.findByPublisher(publisherName);
//        if (optionalUser.isPresent()) {
//            if (!optionalEvent.isEmpty()) {
//                return new ArrayList<>(optionalEvent);
//            }
//        }
//        return null;
//    }
//
//    public void receive() {
//
//    }
//
    public boolean sendPost(String message, String publisherName) {
        TopicExchange topicExchange = new TopicExchange(defaultExchange + publisherName);
        if (isPublisherExists(publisherName)) {
            //list<queue> from rabbit(exchange) ; if queue exist send, else did`nt send
            //check if queue

//            List<String> strings = new ArrayList<>();
//            strings.forEach(s -> //send);



            return true;
        }
        return false;
    }
//
//
//    public List<User> showAllPublishers() {
//        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
//
//    }


    public boolean isPublisherExists(String name) {
        return !rabbitTemplate.nullSafeExchange(name).equals("");
    }
}
