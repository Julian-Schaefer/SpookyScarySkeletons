package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.messaging.TopicMessagingBean;
import SpookyScarySkeletons.persistenzlogik.AccountService;
import SpookyScarySkeletons.persistenzlogik.AccountServiceLocal;

import javax.ejb.EJB;
import java.time.Duration;
import java.time.LocalDateTime;

public abstract class AnwendungsLogikBean {

    @EJB
    protected EntscheidungsbaumParserBean entscheidungsbaumParserBean;

    @EJB
    protected TimerManagementBean timerManagementBean;

    @EJB
    protected TopicMessagingBean topicMessagingBean;

    @EJB
    protected AccountServiceLocal accountService;

    protected Message firstMessage;
    protected Message currentMessage;

    private int value = 5;
    protected String username;
    protected String scenarioName;

    protected Message lowValueStartMessage = null;
    private boolean lowValuePath = false;

    private AnwendungslogikListener anwendungslogikListener;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Message getFirstMessage() {
        currentMessage = firstMessage;
        return firstMessage;
    }

    public void getNextMessage(int id) {
        Choice choice = currentMessage.getFirstChoice().getId() == id ?
                currentMessage.getFirstChoice():
                currentMessage.getSecondChoice();

        Message nextMessage;
        //Commented out logic for now to test communication with frontend
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

    public void startGame(String username) {
        System.out.println("Starting game for Username: " + username);
        timerManagementBean.addTimerRequestListener(username, this::onTimerExired);
        this.username = username;

        startTime = LocalDateTime.now();
    }

    public void onTimerExired(TimerManagementBean.TimerRequest timerRequest) {
        if(timerRequest.getType() == TimerManagementBean.Type.MESSAGE) {
            Message message = (Message) timerRequest.getContent();
            if(message == null || (message.getFirstChoice() == null && message.getSecondChoice() == null)) {
                gameOver();
            } else {
                anwendungslogikListener.onNewMessage((Message) timerRequest.getContent());
            }
        }
    }

    public void setAnwendungslogikListener(AnwendungslogikListener anwendungslogikListener) {
        this.anwendungslogikListener = anwendungslogikListener;
    }

    protected void gameOver() {
        endTime = LocalDateTime.now();

        int[] minutesAndSeconds = getMinutesAndSeconds();

        accountService.addScore(username, scenarioName, (minutesAndSeconds[0]*60+minutesAndSeconds[1]));

        System.out.println("User " + username + " took " + minutesAndSeconds[0] + " minutes and " + minutesAndSeconds[1] + " seconds to complete a scenario");
        anwendungslogikListener.onGameOver("You took " + minutesAndSeconds[0] + " minutes and " + minutesAndSeconds[1] + " seconds.");
    }

    protected int[] getMinutesAndSeconds() {
        Duration duration = Duration.between(startTime, endTime);
        long diff = Math.abs(duration.toMillis());
        int minutes = (int) (diff/1000.0/60.0);
        double lastMinute = (diff/1000.0/60.0) % 1;
        int seconds = (int) (lastMinute * 60.0);

        return new int[]{ minutes, seconds };
    }

    protected void setValue(int value, boolean callListener) {
        this.value = value;
        if(callListener) {
            anwendungslogikListener.onValueChanged(username, value);
        }
    }

    protected int getValue() {
        return this.value;
    }

    public abstract void dispose();

    protected abstract void onDestroy();

    public interface AnwendungslogikListener {
        void onValueChanged(String username, int newValue);
        void onNewMessage(Message message);
        void onGameOver(String message);
    }
}
