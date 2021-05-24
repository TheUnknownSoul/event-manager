package com.controller;

import com.exception.NoSuchPublisherException;
import com.service.EventManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;

@RestController
@RequestMapping("/publisher")
public class PublisherRestController extends HttpServlet {

    @Autowired
    EventManagerService eventManagerService;

    @RequestMapping("/registration")
    public String registerPublisher(@RequestParam(name = "name") String name) {
        return eventManagerService.register(name);
    }

    @PostMapping("/{name}/send")
    public String sendPost(@RequestParam("message") String message, @PathVariable String name) throws NoSuchPublisherException {
        return eventManagerService.sendPost(message, name);
    }

}
