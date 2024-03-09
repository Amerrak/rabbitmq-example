package usy.aibhub.amqp.examples;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.apache.qpid.jms.JmsQueue;
import org.apache.qpid.jms.message.JmsBytesMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample implementation of Business Application (BA) AMQP integration.
 *
 * This sample shows how to receive AMQP1.0 messages from AIB HUB Broker - RabbitMQ.
 *
 * @author Dominik Lohniský
 * @since 08.09.2023
 */
public class BaIntegrationSampleMessageReceiver extends BaIntegrationSample{

  private static final Logger LOG = LoggerFactory.getLogger(BaIntegrationSampleMessageReceiver.class);
  public static final int TIMEOUT = 5 * 60 * 1000;

  /**
   * Receive AMQP1.0 message from AIB HUB Broker - RabbitMQ
   * @throws JMSException
   */
  public static void receiveData(String inboxQueueName, String messageBrokerUri, String targetFolder, String username, String password) throws JMSException {
    JmsConnectionFactory jmsConnectionFactory = getJmsConnectionFactory(messageBrokerUri);
    Connection connection = null;
    Session session = null;

    jmsConnectionFactory.setUsername(username);
    jmsConnectionFactory.setPassword(password);

    // using configured inbox queue name
    Queue queue = new JmsQueue(inboxQueueName);
    JmsBytesMessage jmsMessage = null;
    try {
      connection = jmsConnectionFactory.createConnection();
      connection.start();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageConsumer messageConsumer = session.createConsumer(queue);
      // receive message synchronously, wait for specified timeout (ms)
      // return null if the timeout expires
      jmsMessage = (JmsBytesMessage) messageConsumer.receive(TIMEOUT);
      if (jmsMessage == null){
        LOG.info("Timeout when receiving message. Probably there is no message at queue: {}", queue.getQueueName());
        return;
      }
    } catch (Exception e) {
      throw new RuntimeException("Failed to receive message", e);
    } finally {
      if (session != null) {
        session.close();
      }
      if (connection != null) {
        connection.stop();
        connection.close();
      }
    }
    byte[] bytes = new byte[(int) jmsMessage.getBodyLength()];
    jmsMessage.readBytes(bytes);

    if(targetFolder != null){
      Path targetFolderPath = Paths.get(targetFolder);
      try {
        Files.createDirectories(targetFolderPath);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }

      String filename = new SimpleDateFormat("yyyyMMdd_HHmmss'.txt'").format(new Date());
      try (FileOutputStream outputStream = new FileOutputStream(targetFolderPath.resolve(filename).toString())) {
        outputStream.write(bytes);
        LOG.info("Received filename is: {}", filename);
      }catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    LOG.info("Message was successfully received with content: \"{}\"", new String(bytes));
  }

}
