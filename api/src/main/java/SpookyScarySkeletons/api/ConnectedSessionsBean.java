package SpookyScarySkeletons.api;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.websocket.Session;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Singleton
@Startup
public class ConnectedSessionsBean {

    private static final String USERNAME = "username";

    private List<Session> sessions;
    private List<Session> wrongNumberSessions;
    private List<Session> longJourneySessions;

    @PostConstruct
    private void init() {
        sessions = new LinkedList<>();
        wrongNumberSessions = new LinkedList<>();
        longJourneySessions = new LinkedList<>();
    }

    public void sendToAllSessionsExceptOriginator(String usernameOriginator, String message) {
        try {
            for (Session session : getAllSessions()) {
                String username = (String) session.getUserProperties().get(USERNAME);
                if (!usernameOriginator.equals(username)) {
                    session.getBasicRemote().sendText(message);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
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
        sessions = getOpenSessions(sessions);
        return sessions;
    }

    public List<Session> getWrongNumberSessions() {
        wrongNumberSessions = getOpenSessions(wrongNumberSessions);
        return wrongNumberSessions;
    }

    public List<Session> getLongJourneySessions() {
        longJourneySessions = getOpenSessions(longJourneySessions);
        return longJourneySessions;
    }

    private List<Session> getOpenSessions(List<Session> sessions) {
        List<Session> openSessions = new LinkedList<>();
        for(Session session: sessions) {
            if(session.isOpen()) {
                openSessions.add(session);
            }
        }

        return openSessions;
    }
}
