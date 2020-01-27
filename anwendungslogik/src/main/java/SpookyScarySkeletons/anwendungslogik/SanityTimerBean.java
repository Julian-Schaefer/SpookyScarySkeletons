package SpookyScarySkeletons.anwendungslogik;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Singleton
public class SanityTimerBean {

    private static String SANITY_TIMER = "SANITY_TIMER";

    @Resource
    private TimerService timerService;

    private Map<String, SanityChangeListener> sanityChangeListeners;

    @PostConstruct
    private void init(){
        sanityChangeListeners = new LinkedHashMap<>();
    }

    public void addSanityTimer(String username, SanityChangeListener sanityChangeListener) {
        for(Timer timer : timerService.getAllTimers()) {
            if(timer.getInfo().equals(SANITY_TIMER + username)) {
                return;
            }
        }

        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(SANITY_TIMER + username);

        System.out.println("Creating Sanity Timer for user: " + username);
        timerService.createIntervalTimer(0, 5000, timerConfig);

        sanityChangeListeners.put(username, sanityChangeListener);
    }

    public void removeSanityTimer(String username) {
        sanityChangeListeners.remove(username);
    }

    @Timeout
    public void onTimeOut(Timer timer) {
        if(timer.getInfo().toString().startsWith(SANITY_TIMER)) {
            String username = timer.getInfo().toString().replace(SANITY_TIMER, "");
            if(sanityChangeListeners.containsKey(username)) {
                SanityChangeListener sanityChangeListener = sanityChangeListeners.get(username);
                if(sanityChangeListener != null) {
                    sanityChangeListener.onChangeSanity();
                } else {
                    sanityChangeListeners.remove(username);
                    timer.cancel();
                }
            } else {
                timer.cancel();
            }
        }
    }

    public interface SanityChangeListener {
        void onChangeSanity();
    }
}
