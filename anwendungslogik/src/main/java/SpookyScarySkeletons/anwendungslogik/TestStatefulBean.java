package SpookyScarySkeletons.anwendungslogik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

@Stateful
public class TestStatefulBean {

    @Resource
    private SessionContext sessionContext;

    private double number;

    @PostConstruct
    private void init() {
        number = Math.random();
    }

    public double getNumber() {
        return number;
    }

    @Remove
    public void dispose() {
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Bean will be destroyed");
    }
}
