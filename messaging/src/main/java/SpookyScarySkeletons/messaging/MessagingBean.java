package SpookyScarySkeletons.messaging;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.*;

@Singleton
@Startup
public class MessagingBean {

    private static String STATISTIC_TIMER = "Statistics Timer";

    @Resource
    private TimerService timerService;

    @Inject
    private JMSContext jmsContext;

    @Resource(lookup = "java:app/jms/statisticTopic")
    private Topic topic;

    @PostConstruct
    private void init() {
        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(STATISTIC_TIMER);

        System.out.println("Creating Timer!");
        timerService.createIntervalTimer(0, 5000, timerConfig);
    }

    @Timeout
    public void onTimeOut(Timer timer) {
        if(timer.getInfo().equals(STATISTIC_TIMER)) {
            Message message = jmsContext.createMessage();
            try {
                message.setDoubleProperty("asd", 1.2);
                jmsContext.createProducer().send(topic, message);
                System.out.println("Hallo: " + message.getDoubleProperty("asd"));
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
