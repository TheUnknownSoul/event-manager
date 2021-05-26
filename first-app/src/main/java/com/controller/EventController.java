package com.controller;

import com.service.EventServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.UnsupportedEncodingException;
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

    @PostMapping("{name}/send")
    public void send(@RequestParam("message") String message, @PathVariable("name")String name){
        service.send(message,name);
    }

    @GetMapping("/receive")
    public List<Object> receive(@RequestParam String consumerId) {
        return service.receive(consumerId);
    }

    @PostMapping("/delete")
    public void deleteSubscriber(@RequestParam ("subscriberId") String subscriberId) throws URISyntaxException {
         service.deleteSubscriber(subscriberId);
    }

    @GetMapping("/publishers")
    public List<String> showAllPublishers() throws URISyntaxException {
        return service.showAllPublishers();
    }

}
