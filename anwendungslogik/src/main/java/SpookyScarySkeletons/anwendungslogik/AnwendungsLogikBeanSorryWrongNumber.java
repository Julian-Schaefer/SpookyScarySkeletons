package SpookyScarySkeletons.anwendungslogik;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;

@Stateful
public class AnwendungsLogikBeanSorryWrongNumber extends AnwendungsLogikBean {

    @PostConstruct
    public void init() {
        firstMessage = entscheidungsbaumParserBean.buildTree("/sorrywrongnumber.xml");
    }

}
