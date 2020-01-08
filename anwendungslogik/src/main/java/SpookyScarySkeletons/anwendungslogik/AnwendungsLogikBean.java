package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class AnwendungsLogikBean {

    @EJB
    private EntscheidungsbaumParserBean entscheidungsbaumParserBean;

    private Message currentMessage;
    private Message lowValueStartMessage;
    private int value = 5;
    private boolean lowValuePath = false;

    @PostConstruct
    private void init() {
        currentMessage = entscheidungsbaumParserBean.buildTree("/test.xml");
        lowValueStartMessage = entscheidungsbaumParserBean.buildTree("");
    }

    public Message getNextMessage(Choice choice) {
        value += choice.getValueChange();

        Message nextMessage;
        if (value >= 0 && !lowValuePath) {
            //normal
            nextMessage = choice.getNextMessage();
            currentMessage = nextMessage;
        } else if (lowValuePath) {
            if (value < 0)
                nextMessage = choice.getNextMessage();
            else {
                //lowValuePath beenden
                nextMessage = currentMessage;
                lowValuePath = false;
            }
        } else {
            // !lowValuePath && value < 0; lowValuePath starten
            nextMessage = lowValueStartMessage;
            lowValuePath = true;
        }

        if (nextMessage.getFirstChoice() != null) {
            if (nextMessage.getFirstChoice().getMinValue() >= value) {
                nextMessage.setFirstChoice(null);
            }
        }
        if (nextMessage.getSecondChoice()!= null) {
            if (nextMessage.getSecondChoice().getMinValue() >= value) {
                nextMessage.setSecondChoice(null);
            }
        }

        return nextMessage;
    }

    @Remove
    public void dispose() {
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Bean will be destroyed");
    }
}
