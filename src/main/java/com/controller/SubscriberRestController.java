package com.controller;


import com.model.Event;
import com.service.EventManagerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscriber")
public class SubscriberRestController {

    EventManagerService eventManagerService;
    RabbitTemplate rabbitTemplate;

    @PostMapping("/subscribe")
    public void subscriber(int appId, String publisherName, Event event) { // need to change type of returned value
        eventManagerService.subscribe(appId, publisherName, event);

    }

    @PostMapping("/delete")
    public void deleteSubscriber() { // need to change type of returned value
        rabbitTemplate.destroy();
    }

//    @GetMapping("/consumers")
//    public List<User> showAllConsumers() {
//        return eventManagerService.showAllConsumers();
//    }

}
