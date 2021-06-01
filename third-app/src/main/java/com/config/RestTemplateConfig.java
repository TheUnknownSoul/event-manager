package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean(name = "event-manager-rest-template")
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
