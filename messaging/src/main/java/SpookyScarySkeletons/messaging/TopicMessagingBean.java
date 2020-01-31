package SpookyScarySkeletons.messaging;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.*;

@Stateless
public class TopicMessagingBean {

    private static final String USERNAME = "username";

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:app/jms/statisticTopic")
    private Topic statisticTopic;

    @Resource(lookup = "java:app/jms/gameStartedTopic")
    private Topic gameStartedTopic;

    @Resource(lookup = "java:app/jms/gameOverTopic")
    private Topic gameOverTopic;

    public void sendGameStartedMessage(String username) {
        Message message = jmsContext.createMessage();
        try {
            message.setStringProperty(USERNAME, username);
            jmsContext.createProducer().send(gameStartedTopic, message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendGameOverMessage(String username) {
        Message message = jmsContext.createMessage();
        try {
            message.setStringProperty(USERNAME, username);
            jmsContext.createProducer().send(gameOverTopic, message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
