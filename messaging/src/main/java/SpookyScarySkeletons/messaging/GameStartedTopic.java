package SpookyScarySkeletons.messaging;

import javax.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(
        name = "java:app/jms/gameStartedTopic",
        interfaceName = "javax.jms.Topic",
        destinationName = "Game Started Topic"
)
public class GameStartedTopic {
}
