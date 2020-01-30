package SpookyScarySkeletons.anwendungslogik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AnwendungsLogikBeanSorryWrongNumber extends AnwendungsLogikBean {

    @EJB
    private SanityTimerBean sanityTimerBean;

    @PostConstruct
    public void init() {
        firstMessage = entscheidungsbaumParserBean.buildTree("/sorrywrongnumber.xml");
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Bean will be destroyed");
        sanityTimerBean.removeSanityTimer(username);
    }

    @Override
    public void setUsername(String username) {
        super.setUsername(username);
        sanityTimerBean.addSanityTimer(username, this::onChangeSanity);
    }

    public void onChangeSanity() {
        this.setValue(this.getValue() - 5);
    }
}
