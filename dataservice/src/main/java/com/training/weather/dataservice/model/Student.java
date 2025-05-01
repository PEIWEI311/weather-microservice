package com.training.weather.dataservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "students")
public class Student {
    
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String major;
}
