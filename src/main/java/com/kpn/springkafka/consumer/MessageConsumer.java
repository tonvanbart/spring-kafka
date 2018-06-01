package com.kpn.springkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    public MessageConsumer() {
        log.info("Initialized - Consumer");
    }

    @KafkaListener(topics = {"producerTopic"}, groupId = "foo")
    public void receiveMessage(ConsumerRecord<?,?> consumerRecord) {
        log.debug("receiveMessage({})", consumerRecord.value());
    }

}
