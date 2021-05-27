package com.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public interface EventService {
    void registration(String publisherId);

    void subscribe(String consumerId, String publisherId);

    void send(String message, String publisherId) throws IOException;

    List<Object> receive(String consumerId);

    void deleteSubscriber(String name) throws URISyntaxException;

    List<String> showAllPublishers() throws MalformedURLException, URISyntaxException;
}
