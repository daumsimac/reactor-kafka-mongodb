package com.github.deogicorgi.reactive.producer.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.deogicorgi.reactive.common.message.AbstractKafkaMessage;
import com.github.deogicorgi.reactive.common.model.KafkaProduceResult;
import com.github.deogicorgi.reactive.producer.model.Message;
import com.github.deogicorgi.reactive.producer.model.MessageDto;
import com.github.deogicorgi.reactive.producer.repository.MessageRepository;
import com.github.deogicorgi.reactive.producer.service.KafkaService;
import com.github.deogicorgi.reactive.producer.utils.AppUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 프로듀싱 서비스
 * Kafka 프로듀싱 전 로직 처리
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ProduceService {

    private final KafkaService kafkaService;
    private final FailureMessageService failureMessageService;

    @Autowired
    private MessageRepository repo;

    public Mono<KafkaProduceResult> produceMessage(AbstractKafkaMessage message) {
        return kafkaService.send(message)
                .map(produceResult -> {
                    log.info("Kafka Sender result : Topic >> [{}], message >> [{}]", produceResult.getTopic(), produceResult.getRequestedMessage());
                    //List msgList = Arrays.asList(produceResult.getRequestedMessage());
                    //msgList.forEach(System.out::println);
//                    ObjectMapper mapper = new ObjectMapper();
//                    Gson gson = new Gson();
//                    Message msg = gson.fromJson(produceResult.getRequestedMessage(), Message.class);
//                    repo.save(msg);
//                    System.out.println(msg.toString());
//                        String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(produceResult.getRequestedMessage());
//                        Message msg = mapper.readValue(jsonStr, Message.class);
//                        System.out.println(msg.toString());
                    if (produceResult.hasError()) {
                        // TODO 카프카 프로듀싱 실패일 경우 처리
                        // ex ) 처리하지못한 요청을 몽고등에 저장 후 재시도, 로깅 등등
                        log.error("Kafka produce error : {}", produceResult.getErrorMessage());
                    }else {
//                        ObjectMapper mapper = new ObjectMapper();
                        Gson gson = new Gson();
//                        Message msg = gson.fromJson(produceResult.getRequestedMessage(), Message.class);
                        Mono<Message> messageMono = Mono.just(gson.fromJson(produceResult.getRequestedMessage(), MessageDto.class))
                                .map(AppUtils::dtoToEntity).flatMap(m -> System.out::println);
                        System.out.println(messageMono.toString());
                        messageMono.flatMap(repo::insert);
//                        messageDtoMono.map(AppUtils::entityToDto).flatMap(repo::insert);
//                        System.out.println(msg.toString());
//                        msg.map(repo::insert);
//                        System.out.println("A message is saved successfully!!!");
                    }
                    return produceResult;
                });
    }
}
