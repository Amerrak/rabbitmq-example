package usy.aibhub.amqp.examples;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.apache.qpid.jms.policy.JmsDefaultPrefetchPolicy;

/**
 * See {@link BaIntegrationSampleMessageReceiver} and {@link BaIntegrationSampleMessageSender}
 *
 * @author Dominik Lohniský
 * @since 08.09.2023
 */
abstract class BaIntegrationSample {

  protected static Message convertToJmsMessage(Session session, BaMessage message) throws JMSException {
    BytesMessage jmsBytesMessage = session.createBytesMessage();
    jmsBytesMessage.setJMSDeliveryMode(2);
    jmsBytesMessage.writeBytes(message.getContent() == null ? new byte[]{} : message.getContent());
    return jmsBytesMessage;
  }


  protected static JmsConnectionFactory getJmsConnectionFactory(String messageBrokerUri) {
    JmsConnectionFactory jmsConnectionFactory = new JmsConnectionFactory(messageBrokerUri);
    JmsDefaultPrefetchPolicy jmsPrefetchPolicy = new JmsDefaultPrefetchPolicy();
    jmsPrefetchPolicy.setQueuePrefetch(1);
    jmsConnectionFactory.setPrefetchPolicy(jmsPrefetchPolicy);
    return jmsConnectionFactory;
  }
}
