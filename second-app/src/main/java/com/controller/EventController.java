package com.controller;

import com.service.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
public class EventController {

    @Autowired
    private EventServiceImpl service;

    @PostMapping("/registration")
    public void registration(@RequestParam String name) {
        service.registration(name);
    }

    @PostMapping("/subscribe")
    public void subscribe(@RequestParam String appId, @RequestParam String subscriberName) {
        service.subscribe(appId, subscriberName);
    }

    @PostMapping("/send")
    public void send(@RequestParam("message") String message, @RequestParam("name") String name) {
        service.send(message, name);
    }

    @GetMapping("/receive")
    public List<Object> receive(@RequestParam String name) {
        return service.receive(name);
    }

    @PostMapping("/delete")
    public void deleteSubscriber(@RequestParam("subscriberId") String subscriberId) throws URISyntaxException {
        service.deleteSubscriber(subscriberId);
    }

    @GetMapping("/publishers")
    public List<String> showAllPublishers() throws URISyntaxException {
        return service.showAllPublishers();
    }
}
