package SpookyScarySkeletons.messaging;

import javax.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(
        name = "java:app/jms/statisticTopic",
        interfaceName = "javax.jms.Topic",
        destinationName = "Statistic Topic"
)
public class Config {
}
