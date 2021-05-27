package com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
    @Qualifier("rest-template-config")
    @Autowired
    RestTemplate restTemplate;
    @Value("${app.a.host}")
    private String host;

    @Override
    public void registration(String name) {
        String subscribe_url = host + "/publisher/registration?name={name}";
        restTemplate.postForEntity(subscribe_url, null, null, name);
    }

    @Override
    public void subscribe(String appId, String subscriberName) {
        String subscribe_url = host + "subscriber/subscribe?appId={appId}&subscriberName={subscriberName}";
        restTemplate.postForEntity(subscribe_url, null, null, appId, subscriberName);
    }

    @Override
    public void send(String message, String name) {
        String send_url = host + "publisher/" + name + "/send?message=" + message;
        restTemplate.postForEntity(send_url, HttpMethod.POST, null, message, name);
    }

    @Override
    public List<Object> receive(String name) {
        String receiveUrl = host + "subscriber/" + name + "/receive";
        ResponseEntity<List<Object>> response = restTemplate.exchange(receiveUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Object>>() {
                },
                name);
        return response.getBody();
    }

    @Override
    public void deleteSubscriber(String name) throws URISyntaxException {
        URI deleteSubscriberUri = new URI(host + "/subscriber/delete?subscriberId=" + name);
        restTemplate.postForEntity(deleteSubscriberUri, name, null);
    }

    @Override
    public List<String> showAllPublishers() throws URISyntaxException {
        URI publishersUri = new URI(host + "subscriber/publishers");
        List<String> publishers = new ArrayList<>();
        publishers.add(restTemplate.getForObject(publishersUri, String.class));
        return publishers;
    }
}