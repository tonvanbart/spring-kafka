package com.kpn.springkafka.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class SseController {

    private static final Logger log = LoggerFactory.getLogger(SseController.class);

    @GetMapping(path="/messages", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> messages() {
        log.info("messages()");
        return Flux.just("To Do");
    }

}
