package com.kpn.springkafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageConsumer {

    private final List<FluxSink<String>> clients = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    public MessageConsumer() {
        log.info("Initialized - Consumer");
    }

    /**
     * Receive a message and forward it to all registered clients.
     * @param consumerRecord
     */
    @KafkaListener(topics = {"producerTopic"}, groupId = "foo")
    public void receiveMessage(ConsumerRecord<?, ?> consumerRecord) {
        log.debug("receiveMessage({})", consumerRecord.value());
        clients.forEach(client -> {
            String msg = consumerRecord.value().toString();
            log.debug("forward {} to {}", msg, client);
            client.next(msg);
        });
    }

    /**
     * Register a new client and returns a Flux of messages.
     * @return
     */
    public Flux<String> followMessages() {
        log.info("followMessages()");
        return Flux.create(sink -> {
            log.info("adding client {}", sink);
            clients.add(sink);
            sink.onCancel(() -> {
                log.debug("cancel {}", sink);
                clients.remove(sink);
            });
        });
    }

}
