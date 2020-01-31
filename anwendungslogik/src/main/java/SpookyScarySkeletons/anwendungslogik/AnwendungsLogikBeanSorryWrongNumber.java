package SpookyScarySkeletons.anwendungslogik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateful;

@Stateful
public class AnwendungsLogikBeanSorryWrongNumber extends AnwendungsLogikBean {

    @PostConstruct
    public void init() {
        firstMessage = entscheidungsbaumParserBean.buildTree("/sorrywrongnumber.xml");
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Bean will be destroyed");
        timerManagementBean.removeTimerRequestListener(username);
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
        timerManagementBean.addSanityTimer(username);
    }

    @Override
    public void onTimerExired(TimerManagementBean.TimerRequest timerRequest) {
        if(timerRequest.getType() == TimerManagementBean.Type.SANITY) {
            setValue(getValue() + 5);
        } else {
            super.onTimerExired(timerRequest);
        }
    }
}
