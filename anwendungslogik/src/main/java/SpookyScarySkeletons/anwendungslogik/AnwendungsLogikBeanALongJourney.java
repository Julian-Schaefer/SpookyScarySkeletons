package SpookyScarySkeletons.anwendungslogik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;

@Stateful
public class AnwendungsLogikBeanALongJourney extends AnwendungsLogikBean {

    @EJB
    private TimerManagementBean timerManagementBean;

    @PostConstruct
    public void init() {
        firstMessage = entscheidungsbaumParserBean.buildTree("/alongjourney.xml");
//        todo add xml for lowValuePath
//        lowValueStartMessage = entscheidungsbaumParserBean.buildTree("");
    }

    @Override
    public void startGame(String username) {
        super.startGame(username);
        timerManagementBean.startSanityTimer(username);
        topicMessagingBean.sendGameStartedMessage(username, ScenarioManagement.NAME_LONG_JOURNEY);
    }

    @Override
    public void onTimerExired(TimerManagementBean.TimerRequest timerRequest) {
        if(timerRequest.getType() == TimerManagementBean.Type.SANITY) {
            int newValue = getValue() - 5;
            if(newValue >= -100) {
                setValue(getValue() - 5, true);
            } else {
               gameOver(false);
            }
        } else {
            super.onTimerExired(timerRequest);
        }
    }

    @Override
    protected void gameOver(boolean won) {
        super.gameOver(won);
        timerManagementBean.stopSanityTimer(username);
        int[] minutesAndSeconds = getMinutesAndSeconds();
        topicMessagingBean.sendGameOverMessage(username, ScenarioManagement.NAME_LONG_JOURNEY, minutesAndSeconds[0], minutesAndSeconds[1]);
    }

    @Remove
    @Override
    public void dispose() {
    }

    @PreDestroy
    @Override
    public void onDestroy() {
        System.out.println("AnwendungslogikBean for User " + username + " will be destroyed");
        timerManagementBean.removeTimerRequestListener(username);
    }
}