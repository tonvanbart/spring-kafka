package com.kpn.springkafka.controllers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class SseController {

    private static final Logger log = LoggerFactory.getLogger(SseController.class);

    @GetMapping(path = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> events() {
        log.info("events()");
        return Flux.interval(Duration.ofMillis(10))
                .delayElements(Duration.ofMillis(50))
                .doOnEach(nr -> log.info("produce message {}", nr))
                .map(nr -> String.format("message number %s", nr));
    }

    @GetMapping(path="/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> messages() {
        log.info("messages()");
        return Flux.just("To Do");
    }

    @KafkaListener(topics = "producerTopic")
    public void receiveMessage(ConsumerRecord<?,?> consumerRecord) {
        log.debug("receiveMessage({})", consumerRecord.value());
    }


}
