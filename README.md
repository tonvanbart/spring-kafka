## Spring Boot 2.0 with Kafka

This is a small demo app which listens to a Kafka topic, and provides an endpoint
which returns the messages it sees as server sent events.

To run it, I use a Spotify Docker image which contains Kafka and Zookeeper (so I don't have to install locally):

    docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=127.0.0.1 --env ADVERTISED_PORT=9092 --name kafka spotify/kafka

The application contains a message producer which will send a message every 2 seconds.
You can get a HTTP event stream showing the messages using `curl`:

    curl localhost:8080/messages

