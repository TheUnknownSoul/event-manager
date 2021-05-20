package com.controller;

import com.model.User;
import com.service.EventManagerService;
import com.service.RabbitMQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/{name}/send")
    public void sendPost(HttpServletRequest request, HttpServletResponse response, String message,@PathVariable String name) throws IOException {
        request.getParameterValues(String.valueOf(message));
        if (eventManagerService.isPublisherExists(name)){
            rabbitMQService.sendMessage(message);
            eventManagerService.saveMessageInDatabase(message, name);
            response.getWriter().println("Message \" " + message + " \" has been sent" + " in " + name );

        }
            response.getWriter().println("No such publisher has been founded");

    }

    @GetMapping("/publishers")
    public List<User> showAllPublishers() {
        return eventManagerService.showAllPublishers();
    }
}
