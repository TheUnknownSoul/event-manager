package com;


import com.service.EventManagerService;
import com.service.RabbitMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Slf4j
@EnableMongoRepositories(basePackages = "com.repository")
@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class } )
public class EventManagerRunner {

    @Autowired
    EventManagerService eventManagerService;
    @Autowired
    RabbitMQService rabbitMQService;

    public static void main(String... args) {
        SpringApplication.run(EventManagerRunner.class);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory mongoDbFactory, MongoMappingContext context) {

        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), context);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return new MongoTemplate(mongoDbFactory, converter);

    }
}
