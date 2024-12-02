package com.kafka.consumer.repo;

import com.kafka.consumer.model.StudentRecordNotSent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRecord extends JpaRepository<StudentRecordNotSent, Long> {

}
