package com.service;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class EventManagerService {

    @Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    RabbitTemplate rabbitTemplate;
    MessageListenerAdapter listenerAdapter;
    @Value("${spring.rabbitmq.template.exchange}")
    private String defaultExchange;

    private int counter = 0;
    Map<String, List<Queue>> publishersAndQueues = new HashMap<>();

    public void register(String name) {
        TopicExchange topicExchange = new TopicExchange(defaultExchange + name);
        amqpAdmin.declareExchange(topicExchange);
        publishersAndQueues.put(name, null);
    }

    public boolean subscribe(String publisherName) {
        if (isPublisherExists(publisherName)) {
            Queue queue = new Queue(publisherName + "." + counter++);
            amqpAdmin.declareQueue(queue);
            String exchange = defaultExchange + publisherName;
            Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange, exchange, null);
            amqpAdmin.declareBinding(binding);
            publishersAndQueues.computeIfAbsent(publisherName, k -> new ArrayList<>()).add(queue);
            for (Map.Entry<String, List<Queue>> entry : publishersAndQueues.entrySet()) {
                log.info(entry.getKey() + " " + entry.getValue());
            }
            return true;
        }
        return false;
    }

    public List<Object> receive(String publisherName) {
        List<Object> messages = new ArrayList<>();

        for (int i = 0; i < publishersAndQueues.values().size(); i++) {
            for (Map.Entry<String, List<Queue>> entry : publishersAndQueues.entrySet()) {
                String routingKey = publisherName + "." + --counter;
                messages.add(rabbitTemplate.receiveAndConvert(routingKey));

            }
        }
        return messages;
    }

    public boolean sendPost(String message, String publisherName) {
        String exchange = defaultExchange + publisherName;

        if (isPublisherExists(publisherName)) {
            for (Map.Entry<String, List<Queue>> entry : publishersAndQueues.entrySet())
                for (Queue ignored : entry.getValue()) {
                    rabbitTemplate.convertAndSend(exchange, exchange, message);
                }
            return true;
        }
        return false;
    }

    public List<String> showAllPublishers() {
        return new ArrayList<>(publishersAndQueues.keySet());

    }

    public boolean isPublisherExists(String name) {
        return !rabbitTemplate.nullSafeExchange(name).equals("") && !(publishersAndQueues.size() == 0);
    }

    public String deleteSubscriber(){

        return "";
    }
}
