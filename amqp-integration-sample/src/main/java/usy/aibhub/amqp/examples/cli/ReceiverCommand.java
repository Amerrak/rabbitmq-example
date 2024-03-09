package usy.aibhub.amqp.examples.cli;

import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import javax.inject.Inject;
import java.nio.file.Paths;
import javax.jms.JMSException;
import usy.aibhub.amqp.examples.BaIntegrationSampleMessageReceiver;
import usy.aibhub.amqp.examples.DefaultValues;

@Command(name = "receive", description = "Send cmd")
public class ReceiverCommand implements Runnable {

  @Inject
  private HelpOption<ReceiverCommand> help;

  @Option(name = {"-r", "--rabbitMqUri"},
    description = "Uri to rabbitMQ")
  private String messageBrokerUri = DefaultValues.RABBITMQ_URI;

  @Option(name = {"-q", "--senderQueue"},
    description = "Receiver queue name")
  private String receiverQueue = DefaultValues.RECEIVER_QUEUE;

  @Option(name = {"-l", "--login"},
    description = "Login")
  private String login = DefaultValues.LOGIN;

  @Option(name = {"-p", "--password"},
    description = "Password")
  private String password = DefaultValues.PASSWORD;

  @Option(name = {"-t", "--target"},
    description = "Target directory for downloading received messages")
  private String target = Paths.get("target/messages").toString();

  @Override
  public void run() {
    try {
      BaIntegrationSampleMessageReceiver.receiveData(receiverQueue, messageBrokerUri, target, login, password);
    } catch (JMSException e) {
      throw new RuntimeException(e);
    }
  }
}
