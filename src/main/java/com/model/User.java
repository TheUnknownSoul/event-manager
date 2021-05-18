package com.model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "users")
public class User {

    @Id()
    private String id;
    @Field(value = "name")
    private String name;
    @Field(value = "role")
    private String role;

    public User(String name, String role) {
        this.name = name;
        this.role = role;
    }
}
