package usy.aibhub.amqp.examples;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.apache.qpid.jms.JmsQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sample implementation of Business Application (BA) AMQP1.0 integration with AIB HUB Broker - RabbitMQ.
 *
 * This sample shows how to send AMQP1.0 message to RabbitMQ. *
 *
 * @author Dominik Lohniský
 * @since 08.09.2023
 */
public class BaIntegrationSampleMessageSender extends BaIntegrationSample {

  private static final Logger LOG = LoggerFactory.getLogger(BaIntegrationSampleMessageSender.class);

  public static void sendData(BaMessage baMessage, String messageBrokerUri, String senderQueue, String username, String password) throws JMSException {
    try {
      sendMessage(baMessage, messageBrokerUri, senderQueue, username, password);
      LOG.info("Message was successfully sent.");
    } catch (Exception e) {
      LOG.info("Sending message has failed.");
      throw e;
    }
  }

  /**
   * Sends AMQP1.0 message to AIB HUB Broker.
   *
   * @param baMessage message to send
   */
  private static void sendMessage(BaMessage baMessage, String messageBrokerUri, String senderQueue, String username, String password) throws JMSException {
    JmsConnectionFactory jmsConnectionFactory = getJmsConnectionFactory(messageBrokerUri);
    Connection connection = null;
    Session session = null;

    jmsConnectionFactory.setUsername(username);
    jmsConnectionFactory.setPassword(password);

    try {
      connection = jmsConnectionFactory.createConnection();
      connection.start();
      session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      // using AMQP outbox queue name
      Queue queue = new JmsQueue(senderQueue);
      MessageProducer producer = session.createProducer(queue);
      producer.setDeliveryMode(2);
      producer.send(convertToJmsMessage(session, baMessage));
    } catch (JMSException e) {
      throw new RuntimeException("Failed to send message", e);
    } finally {
      if (session != null) {
        session.close();
      }
      if (connection != null) {
        connection.stop();
        connection.close();
      }
    }
  }
}
