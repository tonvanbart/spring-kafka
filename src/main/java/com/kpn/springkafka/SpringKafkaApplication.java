package com.kpn.springkafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SpringKafkaApplication {

	public static Logger logger = LoggerFactory.getLogger(SpringKafkaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringKafkaApplication.class, args);
	}

//	@Autowired
	private KafkaTemplate<String, String> template;

	private final CountDownLatch latch = new CountDownLatch(3);

/*
	@Override
	public void run(String... args) throws Exception {
		this.template.send("myTopic", "foo1");
		this.template.send("myTopic2", "foo2");
		this.template.send("myTopic", "foo3");
		latch.await(60, TimeUnit.SECONDS);
		logger.info("All received");
	}
*/

//	@KafkaListener(topics = {"myTopic","myTopic2"})
	public void listen(ConsumerRecord<?, ?> cr) throws Exception {
//		logger.info(cr.toString());
		logger.info("{}:{}", cr.topic(), cr.value());
		latch.countDown();
	}
}
