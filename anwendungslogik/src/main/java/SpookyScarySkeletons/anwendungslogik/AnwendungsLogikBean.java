package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;

import javax.ejb.EJB;
import javax.ejb.Remove;

public abstract class AnwendungsLogikBean {

    @EJB
    protected EntscheidungsbaumParserBean entscheidungsbaumParserBean;

    @EJB
    protected TimerManagementBean timerManagementBean;

    protected Message firstMessage;
    private Message currentMessage;
    protected Message lowValueStartMessage = null;
    private int value = 5;
    private boolean lowValuePath = false;
    protected String username;
    private AnwendungslogikListener anwendungslogikListener;

    public Message getFirstMessage() {
        currentMessage = firstMessage;
        return firstMessage;
    }

    public void getNextMessage(int id) {
        try{
            Thread.sleep(2000);
        } catch(Exception e) {
            e.printStackTrace();
        }

        Choice choice;
        if(currentMessage.getFirstChoice().getId() == id) {
            choice = currentMessage.getFirstChoice();
        } else {
            choice = currentMessage.getSecondChoice();
        }

        value += choice.getValueChange();

        Message nextMessage;
        //Commented out logik for now to test communication with frontend
//        if (value >= 0 && !lowValuePath) {
            //normal
            nextMessage = choice.getNextMessage();
            currentMessage = nextMessage;
//        } else if (lowValuePath) {
//            if (value < 0)
//                nextMessage = choice.getNextMessage();
//            else {
//                //lowValuePath beenden
//                nextMessage = currentMessage;
//                lowValuePath = false;
//            }
//        } else {
//            // !lowValuePath && value < 0; lowValuePath starten
//            nextMessage = lowValueStartMessage;
//            lowValuePath = true;
//        }
//
//        if (nextMessage.getFirstChoice() != null) {
//            if (nextMessage.getFirstChoice().getMinValue() >= value) {
//                nextMessage.setFirstChoice(null);
//            }
//        }
//        if (nextMessage.getSecondChoice()!= null) {
//            if (nextMessage.getSecondChoice().getMinValue() >= value) {
//                nextMessage.setSecondChoice(null);
//            }
//        }

        timerManagementBean.addMessageTimer(username, nextMessage);
    }

    public void onChangeSanity(TimerManagementBean.TimerRequest timerRequest) {
        if(timerRequest.getType() == TimerManagementBean.Type.SANITY) {
            this.setValue(this.getValue() - 5);
        }
    }

    public void setUsername(String username) {
        System.out.println("Setting username " + this.getClass().getName());
        timerManagementBean.addTimerRequestListener(username, this::onTimerExired);
        this.username = username;
    }

    public void onTimerExired(TimerManagementBean.TimerRequest timerRequest) {
        if(timerRequest.getType() == TimerManagementBean.Type.MESSAGE) {
            Message message = (Message) timerRequest.getContent();
            if(message == null || (message.getFirstChoice() == null && message.getSecondChoice() == null)) {
                anwendungslogikListener.onGameOver();
            } else {
                anwendungslogikListener.onNewMessage((Message) timerRequest.getContent());
            }
        }
    }

    public void setAnwendungslogikListener(AnwendungslogikListener anwendungslogikListener) {
        this.anwendungslogikListener = anwendungslogikListener;
    }

    protected void setValue(int value) {
        this.value = value;
        anwendungslogikListener.onValueChanged(username, value);
    }

    protected int getValue() {
        return this.value;
    }

    public abstract void dispose();

    public interface AnwendungslogikListener {
        void onValueChanged(String username, int newValue);
        void onNewMessage(Message message);
        void onGameOver();
    }
}
