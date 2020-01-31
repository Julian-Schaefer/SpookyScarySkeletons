package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Message;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Singleton
public class TimerManagementBean {

    public enum Type {
        SANITY,
        MESSAGE
    }

    @Resource
    private TimerService timerService;

    private Map<String, TimerRequestListener> timerRequestListeners;

    @PostConstruct
    private void init(){
        timerRequestListeners = new LinkedHashMap<>();
    }

    public void addSanityTimer(String username) {
        TimerRequest timerRequest = new TimerRequest(username, Type.SANITY, null);

        for(Timer timer : timerService.getAllTimers()) {
            try {
                TimerRequest activeTimerRequest = (TimerRequest) timer.getInfo();

                if(activeTimerRequest.getType() == Type.SANITY && activeTimerRequest.getUsername() == activeTimerRequest.getUsername()) {
                    timer.cancel();
                }
            } catch(Exception e) {
            }
        }

        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(timerRequest);

        System.out.println("Creating Sanity Timer for Username: " + timerRequest.getUsername());
        timerService.createIntervalTimer(0, 5000, timerConfig);
    }

    public void addMessageTimer(String username, Message message) {
        TimerRequest timerRequest = new TimerRequest(username, Type.MESSAGE, message);

        TimerConfig timerConfig = new TimerConfig();
        timerConfig.setInfo(timerRequest);

        System.out.println("Creating Message Timer for Username: " + timerRequest.getUsername());
        timerService.createSingleActionTimer((long) (Math.random() * 10000.0), timerConfig);
    }

    public void addTimerRequestListener(String username, TimerRequestListener timerRequestListener) {
        timerRequestListeners.put(username, timerRequestListener);
    }

    public void removeTimerRequestListener(String username) {
        timerRequestListeners.remove(username);
    }

    @Timeout
    public void onTimeOut(Timer timer) {
        try {
            TimerRequest timerRequest = (TimerRequest) timer.getInfo();
            if(timerRequestListeners.containsKey(timerRequest.getUsername())) {
                TimerRequestListener timerRequestListener = timerRequestListeners.get(timerRequest.getUsername());
                if(timerRequestListener != null) {
                    timerRequestListener.onTimerExpired(timerRequest);
                } else {
                    timerRequestListeners.remove(timerRequest.getUsername());
                    timer.cancel();
                }
            } else {
                timer.cancel();
            }
        } catch(Exception e) {
        }
    }

    public static class TimerRequest implements Serializable {
        private String username;
        private Type type;
        private Serializable content;

        public TimerRequest() {
        }

        public TimerRequest(String username, Type type, Serializable content) {
            this.username = username;
            this.type = type;
            this.content = content;
        }

        public String getUsername() {
            return username;
        }

        public Serializable getContent() {
            return content;
        }

        public Type getType() {
            return type;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj != null && obj instanceof TimerRequest) {
                TimerRequest timerRequest = (TimerRequest) obj;
                if(timerRequest.getUsername().equals(username) && timerRequest.getType().equals(type) && timerRequest.getContent().equals(content)) {
                    return true;
                }
            }

            return false;
        }
    }

    public interface TimerRequestListener {
        void onTimerExpired(TimerRequest timerRequest);
    }
}
