package com.github.deogicorgi.reactive.producer.repository;

import com.github.deogicorgi.reactive.producer.model.Message;
import com.github.deogicorgi.reactive.producer.model.MessageDto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MessageRepository extends ReactiveMongoRepository<Message, String> {
}
