package com.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExceptionManagerService {

    RabbitMQService rabbitMQService;

    @Scheduled(cron = "*/10 * * * * *") // check queue every 10 sec and delete message if it stack
    public void deleteMessageFromQueue() {
        rabbitMQService.deleteMessage();
        System.out.println("Message has been successfully removed");
    }
}
