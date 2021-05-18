package com.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.Message;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Event {

    @Id
    private String eventId;
    @Field(name = "date")
    private LocalDateTime dateTime;
    @Field(name = "category")
    private String category;
    @Field(name = "message")
    private Message message;
}
