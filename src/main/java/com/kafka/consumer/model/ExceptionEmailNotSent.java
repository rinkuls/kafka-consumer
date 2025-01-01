package com.kafka.consumer.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
public class ExceptionEmailNotSent {

    private static final long serialVersionUID = 13889645234L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exception_email_not_sent_seq")
    @SequenceGenerator(name = "exception_email_not_sent_seq", sequenceName = "exception_email_not_sent_seq", allocationSize = 1)
    private Long id;
    private Long empId;
    private String firstName;
    private String lastName;
    private boolean exceptionReceived;
    private boolean emailSent;
    private String email;
}
