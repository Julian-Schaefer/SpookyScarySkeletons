package SpookyScarySkeletons.anwendungslogik;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;

@Stateful
public class AnwendungsLogikBean {

    private int counter;

    public Choice getNextChoice() {
        Choice choice = new Choice();
        choice.setMessage("Nachricht: " + ++counter);
        choice.setFirstOption("Antwort 1");
        choice.setSecondOption("Antwort 2");
        return choice;
    }

    @Remove
    public void dispose() {
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Bean will be destroyed");
    }
}
