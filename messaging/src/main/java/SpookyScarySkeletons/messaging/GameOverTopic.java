package SpookyScarySkeletons.messaging;

import javax.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(
        name = "java:app/jms/gameOverTopic",
        interfaceName = "javax.jms.Topic",
        destinationName = "Game Over Topic"
)
public class GameOverTopic {
}
