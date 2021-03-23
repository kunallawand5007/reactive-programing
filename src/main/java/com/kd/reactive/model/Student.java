package com.kd.reactive.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Student {


    @Id
    private String id;
    private String fname;
    private String lname;
    private String gender;
    private Integer age;




}
