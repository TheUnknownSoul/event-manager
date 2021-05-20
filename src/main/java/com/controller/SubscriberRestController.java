package com.controller;


import com.model.Event;
import com.model.User;
import com.service.EventManagerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriber")
public class SubscriberRestController {
    @Autowired
    EventManagerService eventManagerService;
    @Autowired
    RabbitTemplate rabbitTemplate;

//    @PostMapping("/subscribe")
//    public List<Event> subscribe(@RequestParam("name") String publisherName) {
//       return eventManagerService.subscribe(publisherName);
//
//    }

    @PostMapping("/delete")
    public void deleteSubscriber() { // need to change type of returned value

    }

//    @GetMapping("/publishers")
//    public List<User> showAllPublishers() {
//        return eventManagerService.showAllPublishers();
//    }

}
