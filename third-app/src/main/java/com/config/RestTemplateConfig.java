package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean(name = "rest-template-config")
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
