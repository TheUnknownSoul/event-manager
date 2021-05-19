package com.controller;

import com.model.User;
import com.service.EventManagerService;
import com.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/publisher")
public class PublisherRestController extends HttpServlet {
    @Autowired
    RabbitMQService rabbitMQService;
    @Autowired
    EventManagerService eventManagerService;

    @RequestMapping("/registration")
    public void registerPublisher( String name) {
        eventManagerService.register(name);
    }

    @PostMapping("/send")
    public void sendPost(HttpServletRequest request, HttpServletResponse response, String message) throws IOException {
        request.getParameterValues(String.valueOf(message));
        rabbitMQService.sendMessage(message);
        eventManagerService.saveMessageInDatabase(message);
        response.getWriter().println("Message \" " + message + " \" has been sent");
    }

    @GetMapping("/publishers")
    public List<User> showAllPublishers() {
        return eventManagerService.showAllPublishers();
    }
}
