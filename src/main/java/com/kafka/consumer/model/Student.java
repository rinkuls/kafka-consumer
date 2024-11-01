package com.kafka.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Student implements Serializable {

	private static final long serialVersionUID = 1345234L;
	private  Long empId;
	private  String firstName;
	private  String lastName;
	private  Integer age;




}
