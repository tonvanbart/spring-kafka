## Spring Boot 2.0 with Kafka

This is a small demo app which listens to a Kafka topic, and provides an endpoint
which returns the messages it sees as server sent events.

To run it, I use a Spotify Docker image which contains Kafka and Zookeeper (so I don't have to install locally):

    docker run -p 2181:2181 -p 9092:9092 --env ADVERTISED_HOST=127.0.0.1 --env ADVERTISED_PORT=9092 --name kafka spotify/kafka

The application contains a message producer which will send a message every 2 seconds.
You can get a HTTP event stream showing the messages using `curl`:

    curl -i localhost:8080/messages

With authentication enabled, add userid and password:

    curl -i -u user:s3cr3t localhost:8080/messages

If you want to use [HTTPie](https://httpie.org/) you have to pass the `--stream` flag to see the
streamed events:

    http --auth user:s3cr3t --stream :8080/messages

In all cases, the output should look something like this:

    HTTP/1.1 200 OK
    transfer-encoding: chunked
    Content-Type: text/event-stream;charset=UTF-8
    Cache-Control: no-cache, no-store, max-age=0, must-revalidate
    Pragma: no-cache
    Expires: 0
    X-Content-Type-Options: nosniff
    X-Frame-Options: DENY
    X-XSS-Protection: 1 ; mode=block

    data:Message 14

    data:Message 15

A new message should appear every 2 seconds.
Press Ctrl-C to stop listening to the events. The application has some logging to
show you what is going on during the process.

