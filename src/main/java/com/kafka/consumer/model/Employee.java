package com.kafka.consumer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
    private Long id;


    private String name;
    private Long empId;
    private String email;
    private String phoneNumber;
    private String address;
    private boolean married;
    private boolean ExtraMartialAffair;
    private String dreamWish;
    private String natureBehavior;
    @Lob
    private byte[] profilePicture;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Kids> kids;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private Spouse spouse;

    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL)
    private ProfessionalDetails professionalDetails;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PastEmployment> pastEmployments;


}
