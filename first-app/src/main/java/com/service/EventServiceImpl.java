package com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EventServiceImpl implements EventService {

    private static final String HOST = "http://localhost:8080/";
    private static final String REGISTRATION_URL = HOST + "publisher/registration?name=%s";
    private static final String SUBSCRIBE_URL = HOST + "subscriber/subscribe?appId=%s&subscriberName=%s";
    private static final String SEND_URL = HOST + "publisher/%s/send?message=%s";
    private static final String RECEIVE_URL = HOST + "subscriber/%s/receive";
    private static final String DELETE_URL = HOST + "subscriber/delete?subscriberId=%s";
    private static final String PUBLISHERS_URL = HOST + "subscriber/publishers";

    @Qualifier("event-manager-rest-template")
    @Autowired
    RestTemplate restTemplate;


    @Override
    public void registration(String name) {
        String registration_url = String.format(REGISTRATION_URL, name);
        restTemplate.postForEntity(registration_url, null, null, name);
    }

    @Override
    public void subscribe(String appId, String subscriberName) {
        String subscribe_url = String.format(SUBSCRIBE_URL, appId, subscriberName);
        restTemplate.postForEntity(subscribe_url, null, null, appId, subscriberName);
    }

    @Override
    public void send(String message, String name) {
        String send_url = String.format(SEND_URL, name, message);
        restTemplate.postForEntity(send_url, HttpMethod.POST, null, message, name);
    }

    @Override
    public List<Object> receive(String name) {
        String receiveUrl = String.format(RECEIVE_URL, name);
        ResponseEntity<List<Object>> response = restTemplate.exchange(receiveUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Object>>() {
                },
                name);
        return response.getBody();
    }

    @Override
    public void deleteSubscriber(String name) throws URISyntaxException {
        URI deleteSubscriberUri = new URI(String.format(DELETE_URL, name));
        restTemplate.postForEntity(deleteSubscriberUri, name, null);
    }

    @Override
    public List<String> showAllPublishers() throws URISyntaxException {
        URI publishersUri = new URI(PUBLISHERS_URL);
        List<String> publishers = new ArrayList<>();
        publishers.add(restTemplate.getForObject(publishersUri, String.class));
        return publishers;
    }
}