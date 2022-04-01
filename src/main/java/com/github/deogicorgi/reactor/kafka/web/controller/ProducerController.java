package com.github.deogicorgi.reactor.kafka.web.controller;

import com.github.deogicorgi.reactor.kafka.producer.message.AbstractKafkaProduceMessage;
import com.github.deogicorgi.reactor.kafka.producer.model.KafkaProduceResult;
import com.github.deogicorgi.reactor.kafka.web.service.ProduceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 카프카 프로듀서 Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/produce")
public class ProducerController {

    private final ProduceService produceService;

    @PostMapping
    public Mono<KafkaProduceResult> send(@RequestBody AbstractKafkaProduceMessage message) {
        return produceService.produceMessage(message);
    }
}
