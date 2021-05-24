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


    @PostMapping("/subscribe")
    public String subscribe(@RequestParam("appId") String appId, @RequestParam ("subscriberName") String subscriberName) {
        return eventManagerService.subscribe(appId, subscriberName);
    }

    @PostMapping("/delete")
    public String deleteSubscriber(@RequestParam ("subscriberId") String subscriberId) {
        return eventManagerService.deleteSubscriber(subscriberId);
    }

    @GetMapping("/publishers")
    public List<String> showAllPublishers() {
        return eventManagerService.showAllPublishers();
    }

    @GetMapping("/{name}/receive")
    public List<Object> receive(@PathVariable String name) {
        return eventManagerService.receive(name);

    }
}
