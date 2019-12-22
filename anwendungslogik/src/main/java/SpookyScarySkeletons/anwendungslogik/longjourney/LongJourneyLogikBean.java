package SpookyScarySkeletons.anwendungslogik.longjourney;

import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class LongJourneyLogikBean {

    @Remove
    public void dispose() {
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("LongJourneyLogikBean will be destroyed");
    }

}
