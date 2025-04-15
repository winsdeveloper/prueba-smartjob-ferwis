package com.test.users.prueba_java_smartjob.repository;

import com.test.users.prueba_java_smartjob.entity.Phone;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface PhoneRepository extends ReactiveCrudRepository<Phone, Long> {
  Flux<Phone> findByUserId(UUID userId);
}
