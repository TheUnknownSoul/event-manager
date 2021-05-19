package com.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;


@Slf4j
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Service
public class RabbitMQService  {

    private  CountDownLatch latch = new CountDownLatch(1);
    @Autowired
    RabbitTemplate rabbitTemplate;
//    @Value("${spring.rabbitmq.template.exchange}")
//    AMQP.Exchange exchange;
    private int messageNumber;


    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}") // need to define queue
    public void receiveMessage(String message) {
        log.info("Received message '{}'", message);
        System.out.println("Message " + message + " has been received" );

    }

    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}")
    public void sendMessage(String message){

        rabbitTemplate.convertAndSend("event.#","event.manager.routingKey.default",message);
        System.out.println("Message has been sent: " + message);
    }

    public void deleteMessage(){
    rabbitTemplate.destroy();

    }


}

