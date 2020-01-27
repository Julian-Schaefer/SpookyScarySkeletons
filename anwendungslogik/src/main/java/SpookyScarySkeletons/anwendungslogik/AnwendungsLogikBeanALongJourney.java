package SpookyScarySkeletons.anwendungslogik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;

@Stateful
public class AnwendungsLogikBeanALongJourney extends AnwendungsLogikBean {

    @EJB
    private SanityTimerBean sanityTimerBean;

    @PostConstruct
    public void init() {
        firstMessage = entscheidungsbaumParserBean.buildTree("/alongjourney.xml");
//        todo add xml for lowValuePath
//        lowValueStartMessage = entscheidungsbaumParserBean.buildTree("");
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Bean will be destroyed");
        sanityTimerBean.removeSanityTimer(username);
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
        sanityTimerBean.addSanityTimer(username, this::onChangeSanity);
    }

    public void onChangeSanity() {
        this.setValue(this.getValue() + 5);
    }
}