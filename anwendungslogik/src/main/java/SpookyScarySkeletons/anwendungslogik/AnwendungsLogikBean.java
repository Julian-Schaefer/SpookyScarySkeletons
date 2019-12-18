package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class AnwendungsLogikBean {

    @EJB
    private EntscheidungsbaumParserBean entscheidungsbaumParserBean;

    @PostConstruct
    private void init() {
        entscheidungsbaumParserBean.buildTree("alongjourney.xml");
    }

    public Message getNextMessage(Choice choice) {
        return choice.getNextMessage();
    }

    @Remove
    public void dispose() {
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("Bean will be destroyed");
    }
}
