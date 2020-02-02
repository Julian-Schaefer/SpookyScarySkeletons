package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.messaging.TopicMessagingBean;
import SpookyScarySkeletons.persistenzlogik.AccountServiceLocal;
import SpookyScarySkeletons.persistenzlogik.model.Score;

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

        if(choice.getType() == Choice.Type.NORMAL) {
            Message nextMessage = choice.getNextMessage();
            currentMessage = nextMessage;
            timerManagementBean.addMessageTimer(username, nextMessage);
        } else {
            boolean won = choice.getType() == Choice.Type.WON;
            timerManagementBean.addGameOverTimer(username, won);
        }
        //Commented out logic for now to test communication with frontend
//        if (value >= 0 && !lowValuePath) {
            //normal
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
    }

    public void startGame(String username) {
        System.out.println("Starting game for Username: " + username);
        timerManagementBean.addTimerRequestListener(username, this::onTimerExired);
        this.username = username;

        startTime = LocalDateTime.now();
        topicMessagingBean.sendGameStartedMessage(username, scenarioName);
    }

    public void onTimerExired(TimerManagementBean.TimerRequest timerRequest) {
        if(timerRequest.getType() == TimerManagementBean.Type.MESSAGE) {
            Message message = (Message) timerRequest.getContent();
            anwendungslogikListener.onNewMessage(message);
        } else if(timerRequest.getType() == TimerManagementBean.Type.GAME_OVER) {
            boolean won = (boolean) timerRequest.getContent();
            gameOver(won);
        }
    }

    public void setAnwendungslogikListener(AnwendungslogikListener anwendungslogikListener) {
        this.anwendungslogikListener = anwendungslogikListener;
    }

    protected void gameOver(boolean won) {
        endTime = LocalDateTime.now();

        Score score = new Score(username, scenarioName, getDuration());

        if(won) {
            accountService.addScore(score);
        }

        System.out.println("User " + username + " took " + score.getMinutes() + " minutes and " + score.getSeconds() + " seconds to complete a scenario");
        anwendungslogikListener.onGameOver(won, "You took " + score.getMinutes() + " minutes and " + score.getSeconds() + " seconds.");
        topicMessagingBean.sendGameOverMessage(username, scenarioName, score.getMinutes(),score.getSeconds());
    }


    protected long getDuration() {
        Duration duration = Duration.between(startTime, endTime);
        return Math.abs(duration.toMillis());
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
        void onGameOver(boolean won, String message);
    }
}
