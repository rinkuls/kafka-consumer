package com.kafka.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PastEmployment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "past_employment_seq")
    @SequenceGenerator(name = "past_employment_seq", sequenceName = "past_employment_seq", allocationSize = 1)
    private Long id;

    private String companyName;
    private String designation;
    private double salary;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

}