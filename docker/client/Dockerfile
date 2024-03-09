FROM tomcat:9.0.64-jdk11-openjdk

WORKDIR /
ADD amqp-integration-sample-client.jar amqp-integration-sample-client.jar
ADD message.json message.json
EXPOSE 8080
CMD java -cp amqp-integration-sample-client.jar usy.aibhub.amqp.examples.cli.SampleCli send -b message.json -r amqp://host.docker.internal:5672 -q TARGET-QUEUE
