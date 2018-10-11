package com.kpn.springkafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FiboProducer {

  private KafkaTemplate kafkaTemplate;

  private int first = 0;

  private int second = 1;

  private static final Logger log = LoggerFactory.getLogger(FiboProducer.class);

  @Autowired
  public FiboProducer(KafkaTemplate kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
    log.info("Initialized");
  }

  @Scheduled(fixedRate = 1000L)
  public synchronized void sendMessage() {
    int nextSecond = first + second;

    if (isOverflowed(nextSecond)) {
      first = 0;
      second = 1;
      nextSecond = 1;
    }
    first = second;
    second = nextSecond;

    log.info("next Fibonacci number: {}", first);
    kafkaTemplate.send("fibonacci", String.format("Message %s", first));
  }

  private boolean isOverflowed(int number) {
    return number < 0;
  }

}
