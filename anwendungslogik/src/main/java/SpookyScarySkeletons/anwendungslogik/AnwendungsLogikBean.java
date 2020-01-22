package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remove;

public abstract class AnwendungsLogikBean {

    @EJB
    protected EntscheidungsbaumParserBean entscheidungsbaumParserBean;

    protected Message firstMessage;
    private Message currentMessage;
    protected Message lowValueStartMessage = null;
    private int value = 5;
    private boolean lowValuePath = false;

    @PostConstruct
    protected abstract void init();

    public Message getFirstMessage() {
        currentMessage = firstMessage;
        return firstMessage;
    }

    public Message getNextMessage(int id) {
        Choice choice;
        if(currentMessage.getFirstChoice().getId() == id) {
            choice = currentMessage.getFirstChoice();
        } else {
            choice = currentMessage.getSecondChoice();
        }

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
