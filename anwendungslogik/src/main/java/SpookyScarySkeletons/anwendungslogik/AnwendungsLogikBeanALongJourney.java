package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.persistenzlogik.model.Score;

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
        scenarioName = ScenarioManagement.NAME_LONG_JOURNEY;
//        todo add xml for lowValuePath
//        lowValueStartMessage = entscheidungsbaumParserBean.buildTree("");
    }

    @Override
    public void startGame(String username) {
        super.startGame(username);
        timerManagementBean.startSanityTimer(username);
    }

    @Override
    public void onTimerExired(TimerManagementBean.TimerRequest timerRequest) {
        if(timerRequest.getType() == TimerManagementBean.Type.SANITY) {
            int newValue = getValue() - 1;
            if(newValue > 0) {
                setValue(getValue() - 1, true);
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

    @Override
    public void getNextMessage(int id) {
        Choice choice = currentMessage.getFirstChoice().getId() == id ?
                currentMessage.getFirstChoice():
                currentMessage.getSecondChoice();

        setValue(getValue() + choice.getValueChange(), false);
        super.getNextMessage(id);
    }

    @Override
    public Message getFirstMessage() {
        setValue(90, false);
        return super.getFirstMessage();
    }
}