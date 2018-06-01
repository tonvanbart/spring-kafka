package com.kpn.springkafka.controllers;

import com.kpn.springkafka.consumer.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class SseController {

    private final MessageConsumer messageConsumer;

    private static final Logger log = LoggerFactory.getLogger(SseController.class);

    @Autowired
    public SseController(MessageConsumer messageConsumer) {
        this.messageConsumer = messageConsumer;
        log.info("initialized: {}", messageConsumer);
    }

    @GetMapping(path="/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> messages() {
        log.info("messages()");
        return messageConsumer.followMessages();
    }

    @GetMapping(path="/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> test() {
        log.info("test()");
        return Flux.just("To Do");
    }

}
