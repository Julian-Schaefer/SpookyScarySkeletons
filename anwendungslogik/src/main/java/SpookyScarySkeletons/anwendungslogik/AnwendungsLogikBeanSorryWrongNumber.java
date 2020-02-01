package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class AnwendungsLogikBeanSorryWrongNumber extends AnwendungsLogikBean {

    @PostConstruct
    public void init() {
        firstMessage = entscheidungsbaumParserBean.buildTree("/sorrywrongnumber.xml");
    }

    @Override
    public void startGame(String username) {
        super.startGame(username);
        topicMessagingBean.sendGameStartedMessage(username, ScenarioManagement.NAME_SORRY_WRONG_NUMBER);
    }

    @Override
    public void getNextMessage(int id) {
        Choice choice = currentMessage.getFirstChoice().getId() == id ?
                currentMessage.getFirstChoice():
                currentMessage.getSecondChoice();

        setValue(getValue() + choice.getValueChange(), false);
        super.getNextMessage(id);
    }

    @Override
    public void onTimerExired(TimerManagementBean.TimerRequest timerRequest) {
        super.onTimerExired(timerRequest);
        setValue(getValue(), true);
    }

    @Override
    protected void gameOver(boolean won) {
        super.gameOver(won);
        int[] minutesAndSeconds = getMinutesAndSeconds();
        topicMessagingBean.sendGameOverMessage(username, ScenarioManagement.NAME_SORRY_WRONG_NUMBER, minutesAndSeconds[0], minutesAndSeconds[1]);
    }

    @Remove
    @Override
    public void dispose() {
    }

    @PreDestroy
    @Override
    public void onDestroy() {
        System.out.println("Bean will be destroyed");
        timerManagementBean.removeTimerRequestListener(username);
    }
}
