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
        timerManagementBean.addSanityTimer(username);
    }

    @Override
    public void onTimerExired(TimerManagementBean.TimerRequest timerRequest) {
        if(timerRequest.getType() == TimerManagementBean.Type.SANITY) {
            int newValue = getValue() - 5;
            if(newValue >= -100) {
                setValue(getValue() - 5, true);
            } else {
               gameOver();
            }
        } else {
            super.onTimerExired(timerRequest);
        }
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Bean will be destroyed");
        timerManagementBean.removeTimerRequestListener(username);
    }

    @Remove
    @Override
    public void dispose() {
    }
}