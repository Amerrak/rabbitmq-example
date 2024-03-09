# Sample of AMQP1.0 Java Client

Sample implementation for sending and receiving messages is described inside the following classes:

- `usy.aibhub.amqp.examples.BaIntegrationSampleMessageReceiver`
- `usy.aibhub.amqp.examples.BaIntegrationSampleMessageSender`

Examples can be run against AMQP1.0 RabbitMQ which URI is configured using CLI parameter (-r).

You can display help with all required parameters by running SampleCli class with following parameters:

- `usy.aibhub.amqp.examples.cli.SampleCli help send`
- `usy.aibhub.amqp.examples.cli.SampleCli help receive`

Authentication and authorization is based on standard username/password (see -l --login and -p --password options).

Use the following class to run example:

- `usy.aibhub.amqp.examples.cli.SampleCli`

Example of sending message to RabbitMQ:
- `usy.aibhub.amqp.examples.cli.SampleCli send -b <path to xml file with content> -r amqp://localhost:5672 -q TARGET-QUEUE`

Example of receiving message from RabbitMQ:
- `usy.aibhub.amqp.examples.cli.SampleCli receive -t <path to folder for download> -r amqp://localhost:5672 -q TARGET-QUEUE`
