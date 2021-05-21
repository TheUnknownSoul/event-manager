package com.controller;


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
    private static final String SUCCESS_SUBSCRIBE = "Subscribing has been successful";
    private static final String FAIL_SUBSCRIBE = "Subscribing has been failed";

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam("name") String publisherName) {
        if (eventManagerService.subscribe(publisherName)) {
            return SUCCESS_SUBSCRIBE;
        }
        return FAIL_SUBSCRIBE;

    }

    @PostMapping("/delete")
    public void deleteSubscriber() { // need to change type of returned value

    }

    @GetMapping("/publishers")
    public List<String> showAllPublishers() {
        return eventManagerService.showAllPublishers();
    }

    @GetMapping("/{name}/receive")
    public List<Object> receive(@PathVariable String name){
       return eventManagerService.receive(name);

    }
}
