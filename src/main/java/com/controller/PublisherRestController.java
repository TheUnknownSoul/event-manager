package com.controller;

import com.exception.NoSuchPublisherException;
import com.service.EventManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;

@RestController
@RequestMapping("/publisher")
public class PublisherRestController extends HttpServlet {

//    @Autowired
//    RabbitMQService rabbitMQService;
    @Autowired
    EventManagerService eventManagerService;
    private static final String SUCCESS_MESSAGE = "Message has been successfully  sent ";

    @RequestMapping("/registration")
    public void registerPublisher( String name) {
        eventManagerService.register(name);
    }

    @PostMapping("/{name}/send")
    public String sendPost( String message,@PathVariable String name) throws NoSuchPublisherException {

        if (eventManagerService.sendPost(message, name)){

            return SUCCESS_MESSAGE + "\" " +  message + "\"" + " in  " +  name;
        }

        throw new NoSuchPublisherException("No such publisher");


    }


}
