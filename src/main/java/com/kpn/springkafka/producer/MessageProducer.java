package com.kpn.springkafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Component
public class MessageProducer {

    private final KafkaTemplate kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    public MessageProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        log.info("Initialized - {}", kafkaTemplate);
        kafkaTemplate.send("producerTopic", String.format("Hello, %s", LocalDate.now()));
    }

    /**
     * Returns a Flux which emits a random UUID string every second.
     * @return
     */
    public Flux<String> msgflux() {
        return Flux.fromArray(new String[] {"een","twwee","drie"});
//        return Flux.interval(Duration.ofSeconds(1))
//                .map(l -> UUID.randomUUID().toString());
    }
}
