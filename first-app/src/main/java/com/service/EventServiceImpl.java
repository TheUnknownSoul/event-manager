package com.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
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
        String send_url = host + "publisher/"+name +"/send";

        restTemplate.postForEntity(send_url, message,ResponseEntity.class);

    }

    @Override
    public List<Object> receive(String name) {
        String receive_url = host + "{name}/receive";
        ResponseEntity<List<Object>> response = restTemplate.exchange(receive_url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Object>>() {
                },
                name);
        return response.getBody();
    }

    @Override
    public void deleteSubscriber(String name) throws URISyntaxException {
        URI deleteSubscriberUri = new URI(host + "delete/subscriberId={name}");
        restTemplate.execute(deleteSubscriberUri, HttpMethod.POST, null, null);
    }

    @Override
    public List<String> showAllPublishers() throws URISyntaxException {
        URI publishers_url = new URI(host + "subscriber/publishers");
        RequestEntity<String> request = new RequestEntity<>(HttpMethod.GET, publishers_url);
        //restTemplate?
        return new ArrayList<>(Collections.singleton(request.getBody()));
    }

}
