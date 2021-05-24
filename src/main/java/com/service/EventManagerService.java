package com.service;


import com.exception.NoSuchPublisherException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class EventManagerService {


    private static final String QUEUE_PREFIX = "queue_";
    private static final String EXCHANGE_PREFIX = "exchange_";

    private static final String SUCCESS_MESSAGE = "Message has been successfully sent";
    private static final String REGISTRATION_SUCCESSFUL = "Registration has been success";

    private static final String SUCCESS_SUBSCRIBE = "Subscribing has been successful";
    private static final String FAIL_SUBSCRIBE = "Subscribing has been failed";
    private static final String DELETION_SUCCESS = "Deleting has been successful";
    private static final String DELETION_FAIL = "Deleting has been failed";

    @Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    RabbitTemplate rabbitTemplate;

    List<String> publishers = new ArrayList<>();

    public String register(String name) {
        TopicExchange topicExchange = new TopicExchange(EXCHANGE_PREFIX + name);
        amqpAdmin.declareExchange(topicExchange);
        publishers.add(topicExchange.getName());
        return REGISTRATION_SUCCESSFUL;
    }

    public String sendPost(String message, String publisherName) throws NoSuchPublisherException {
        String exchange = EXCHANGE_PREFIX + publisherName;
        if (isPublisherExists(exchange)) {
            rabbitTemplate.convertAndSend(exchange, exchange, message);
            return SUCCESS_MESSAGE;
        }
        throw new NoSuchPublisherException("No such publisher has been register");
    }

    public List<String> showAllPublishers() {
        return publishers;
    }

    public String subscribe(String appId, String subscriberName) {
        String exchange = EXCHANGE_PREFIX + appId;
        if (isPublisherExists(exchange)) {
            Queue queue = new Queue(QUEUE_PREFIX + subscriberName);
            amqpAdmin.declareQueue(queue);
            Binding binding = new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange, exchange, null);
            amqpAdmin.declareBinding(binding);

            return SUCCESS_SUBSCRIBE;
        }
        return FAIL_SUBSCRIBE;
    }

    public List<Object> receive(String subscriberName) {
        List<Object> events = new ArrayList<>();
        int messageCount = Objects.requireNonNull(amqpAdmin.getQueueInfo(QUEUE_PREFIX + subscriberName)).getMessageCount();
        for (int i = 0; i < messageCount; i++) {
            events.add(rabbitTemplate.receiveAndConvert(QUEUE_PREFIX + subscriberName));
        }

        return events;
    }

    public boolean isPublisherExists(String name) {
        return !rabbitTemplate.nullSafeExchange(name).equals("") && !(publishers.size() == 0) && publishers.contains(name);
    }

    public String deleteSubscriber(String subscriberId) {

        if (amqpAdmin.deleteQueue(QUEUE_PREFIX + subscriberId)) {
            return DELETION_SUCCESS;
        }
        return DELETION_FAIL;
    }
}
