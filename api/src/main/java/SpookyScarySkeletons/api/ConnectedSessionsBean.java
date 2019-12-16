package SpookyScarySkeletons.api;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.websocket.Session;
import java.util.LinkedList;
import java.util.List;

@Singleton
@Startup
public class ConnectedSessionsBean {

    private List<Session> sessions;

    @PostConstruct
    private void init() {
        sessions = new LinkedList<>();
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public List<Session> getSessions() {
        return sessions;
    }
}
