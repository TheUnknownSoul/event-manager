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
    private static final String SUCCESS_MESSAGE = "Message has been successfully  sent ";
    private static final String REGISTRATION_SUCCESSFUL = "Registration has been success";

    @RequestMapping("/registration")
    public String registerPublisher(@RequestParam(name = "name") String name) {

        eventManagerService.register(name);
        return REGISTRATION_SUCCESSFUL;
    }

    @PostMapping("/{name}/send")
    public String sendPost(String message, @PathVariable String name) throws NoSuchPublisherException {

        if (eventManagerService.sendPost(message, name)) {
            return SUCCESS_MESSAGE + "\" " + message + "\"" + " in  " + name;
        }

        throw new NoSuchPublisherException("No such publisher");
    }


}
