# RabbitMQ HTTP Authn/Authz Backend With AMQP1.0 Example

## Structure:
* [amqp-integration-sample](amqp-integration-sample) - Example of Java Client 
* [docker](docker) - Docker settings for RabbitMQ and  [amqp-integration-sample](amqp-integration-sample)
* [rabbitmq_auth_backend_spring_boot](rabbitmq_auth_backend_spring_boot) - HTTP Authn/Authz Backend in Spring

## Running the Example:
#### 1) Run the HTTP Authn/Authz Backend

Import the example as a Maven project in your favorite IDE or run it directly from the command line:
``` shell
mvn spring-boot:run
```

#### 2) Start RabbitMQ service
Compose RabbitMQ using `docker-compose up` in the root of this project.
```bash
docker-compose -f docker-compose.yml
```

#### 3) Send message to RabbitMQ
You can send message using prepared Java Client running one of the following commands: (choose the one that suits you more).

Source codes of client are in [amqp-integration-sample](amqp-integration-sample) folder. 

Directly using .jar file:
```bash
cd docker/client
java -cp amqp-integration-sample-client.jar usy.aibhub.amqp.examples.cli.SampleCli send -b message.json -r amqp://localhost:5672 -q TARGET-QUEUE
```
Or using Docker:
```bash
docker-compose -f docker/client/docker-compose.yml up --build
```

Parameters that can be passed to the client are:

        -b <messageBodyFilePath>, --messageBodyFile <messageBodyFilePath>
            Path to file with content to be sent to RabbitMQ queue

        -l <login>, --login <login>
            Username

        -p <password>, --password <password>
            Password

        -q <senderQueue>, --senderQueue <senderQueue>
            Queue name

        -r <messageBrokerUri>, --rabbitMqUri <messageBrokerUri>
            Uri to rabbitMQ
