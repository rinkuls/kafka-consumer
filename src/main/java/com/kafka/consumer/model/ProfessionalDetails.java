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
public class ProfessionalDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professional_details_seq")
    @SequenceGenerator(name = "professional_details_seq", sequenceName = "professional_details_seq", allocationSize = 1)
    private Long id;

    private String currentCompany;
    private String currentDesignation;
    private double currentSalary;
    private String currentLocation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    // Getters and Setters
}