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
    private List<Session> wrongNumberSessions;
    private List<Session> longJourneySessions;

    @PostConstruct
    private void init() {
        sessions = new LinkedList<>();
    }

    public void addWrongNumberSession(Session session) {
        sessions.add(session);
        wrongNumberSessions.add(session);
    }

    public void removeWrongNumberSession(Session session) {
        sessions.remove(session);
        wrongNumberSessions.remove(session);
    }

    public void addLongJourneySession(Session session) {
        sessions.add(session);
        longJourneySessions.add(session);
    }

    public void removeLongJourneySession(Session session) {
        sessions.remove(session);
        longJourneySessions.remove(session);
    }

    public List<Session> getAllSessions() {
        return sessions;
    }

    public List<Session> getWrongNumberSessions() {
        return wrongNumberSessions;
    }

    public List<Session> getLongJourneySessions() {
        return longJourneySessions;
    }
}
