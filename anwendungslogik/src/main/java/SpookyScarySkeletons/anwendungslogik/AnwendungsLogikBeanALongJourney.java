package SpookyScarySkeletons.anwendungslogik;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

@Stateful
public class AnwendungsLogikBeanALongJourney extends AnwendungsLogikBean {
    @PostConstruct
    public void init() {
        firstMessage = entscheidungsbaumParserBean.buildTree("/alongjourney.xml");
//        todo add xml for lowValuePath
//        lowValueStartMessage = entscheidungsbaumParserBean.buildTree("");
    }
}
