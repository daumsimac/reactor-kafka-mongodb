package com.github.deogicorgi.reactive.consumer.listener;

import com.github.deogicorgi.reactive.consumer.config.model.Message;
import com.github.deogicorgi.reactive.consumer.config.model.MessageDto;
import com.github.deogicorgi.reactive.consumer.repository.MessageRepository;
import com.github.deogicorgi.reactive.consumer.utils.AppUtils;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;

import java.util.List;

/**
 * 카프카 리시버
 */
@Slf4j
@Component
@Service
public class KafkaMessageReceiver {

    @Autowired
    MessageRepository repo;

    /**
     * KafkaMessageReceiver가 생성될 때 모든 카프카 리시버 시작
     */
    public KafkaMessageReceiver(List<KafkaReceiver<Integer, String>> kafkaReceivers) {
        System.out.println("KafakMessageReceiver is started...");
        for (KafkaReceiver<Integer, String> receiver : kafkaReceivers) {
            this.start(receiver);
        }
    }

    public void start(KafkaReceiver<Integer, String> receiver) {
        receiver.receive().subscribe(record -> {
            Gson gson = new Gson();
            Mono<Message> messageMono = Mono.just(gson.fromJson(record.value(), MessageDto.class))
                    .map(AppUtils::dtoToEntity);
            System.out.println(messageMono);
            messageMono.subscribe(value -> repo.save(value).subscribe(System.out::println),
                    error -> error.printStackTrace(),
                    () -> System.out.println("Terminiated..."));
            log.info("Kafka Reciever result : Topic >> [{}], message >> [{}], Offset >> [{}]", record.topic(), record.value(), record.receiverOffset());
        });
    }
}
