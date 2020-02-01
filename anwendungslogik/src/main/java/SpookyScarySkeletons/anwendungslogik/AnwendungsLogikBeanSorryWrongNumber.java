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
