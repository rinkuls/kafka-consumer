package com.kafka.consumer.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotSentRepo extends JpaRepository<com.kafka.consumer.model.ExceptionEmailNotSent, Long> {

}
