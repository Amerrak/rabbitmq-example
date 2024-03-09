package usy.aibhub.amqp.examples.cli;

import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.google.common.io.Resources;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.jms.JMSException;
import usy.aibhub.amqp.examples.BaIntegrationSampleMessageSender;
import usy.aibhub.amqp.examples.BaMessage;
import usy.aibhub.amqp.examples.DefaultValues;

@Command(name = "send", description = "Send cmd")
public class SenderCommand implements Runnable {

  @Inject
  private HelpOption<SenderCommand> help;

  @Option(name = {"-r", "--rabbitMqUri"},
    description = "Uri to rabbitMQ")
  private String messageBrokerUri = DefaultValues.RABBITMQ_URI;

  @Option(name = {"-q", "--senderQueue"},
    description = "Sender queue name")
  private String senderQueue = DefaultValues.SENDER_QUEUE;

  @Option(name = {"-b", "--messageBodyFile"},
    description = "Path to file with content to be sent to RabbitMQ queue")
  private String messageBodyFilePath = Resources.getResource("sendCertificates.xml").getPath();

  @Option(name = {"-l", "--login"},
    description = "Login")
  private String login = DefaultValues.LOGIN;

  @Option(name = {"-p", "--password"},
    description = "Password")
  private String password = DefaultValues.PASSWORD;

  @Override
  public void run() {
    BaMessage baMessage = getMessage();
    try {
      BaIntegrationSampleMessageSender.sendData(baMessage, messageBrokerUri, senderQueue, login, password);
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }

  }

  private BaMessage getMessage() {
    BaMessage message;
    try {
      message = new BaMessage(Files.readAllBytes(Paths.get(messageBodyFilePath)));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return message;
  }
}
