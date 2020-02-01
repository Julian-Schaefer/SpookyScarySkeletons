package SpookyScarySkeletons.messaging;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.*;

@Stateless
public class TopicMessagingBean {

    private static final String USERNAME = "username";
    private static final String SCENARIO = "scenario";
    private static final String MINUTES = "minutes";
    private static final String SECONDS = "seconds";

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:app/jms/gameStartedTopic")
    private Topic gameStartedTopic;

    @Resource(lookup = "java:app/jms/gameOverTopic")
    private Topic gameOverTopic;

    public void sendGameStartedMessage(String username, String scenario) {
        Message message = jmsContext.createMessage();
        try {
            message.setStringProperty(USERNAME, username);
            message.setStringProperty(SCENARIO, scenario);
            jmsContext.createProducer().send(gameStartedTopic, message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendGameOverMessage(String username, String scenario, int minutes, int seconds) {
        Message message = jmsContext.createMessage();
        try {
            message.setStringProperty(USERNAME, username);
            message.setStringProperty(SCENARIO, scenario);
            message.setIntProperty(MINUTES, minutes);
            message.setIntProperty(SECONDS, seconds);
            jmsContext.createProducer().send(gameOverTopic, message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
