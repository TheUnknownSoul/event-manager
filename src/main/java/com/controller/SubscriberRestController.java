package com.controller;


import com.model.Event;
import com.service.EventManagerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriber")
public class SubscriberRestController {
    @Autowired
    EventManagerService eventManagerService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostMapping("/subscribe")
    public List<Event> subscribe(@RequestParam("name") String publisherName) {
       return eventManagerService.subscribe(publisherName);

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
