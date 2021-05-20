package com;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Slf4j
@EnableMongoRepositories(basePackages = "com.repository")
@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class } )
public class EventManagerRunner {


    public static void main(String... args) {
        log.info("Application starts");
        SpringApplication.run(EventManagerRunner.class);
    }



}
