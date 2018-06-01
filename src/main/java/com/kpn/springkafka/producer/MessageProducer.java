package com.kpn.springkafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MessageProducer {

    private AtomicInteger counter = new AtomicInteger(0);

    private final KafkaTemplate kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

    @Autowired
    public MessageProducer(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        log.info("Initialized - {}", kafkaTemplate);
        kafkaTemplate.send("producerTopic", String.format("Hello, %s", LocalDate.now()));
    }

    @Scheduled(fixedRate = 2000L)
    public void sendMessage() {
        int counter = this.counter.incrementAndGet();
        log.info("sendMessage({})", counter);
        kafkaTemplate.send("producerTopic", String.format("Message %s", counter));
    }
}
