package com.kafka.consumer.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "StudentRecordNotSent")
public class StudentRecordNotSent {

    private static final long serialVersionUID = 13889645234L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Long empId;
    private String firstName;
    private String lastName;
    private Integer age;
}
