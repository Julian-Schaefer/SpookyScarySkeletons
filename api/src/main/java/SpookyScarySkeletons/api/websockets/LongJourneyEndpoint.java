package SpookyScarySkeletons.api.websockets;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBeanALongJourney;
import SpookyScarySkeletons.anwendungslogik.ScenarioManagement;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

// URL zum Testen: ws://localhost:8080/api/longjourney

@Stateful
@ServerEndpoint(ScenarioManagement.ENDPOINT_LONG_JOURNEY + "/{username}")
public class LongJourneyEndpoint extends ScenarioEndpoint {

    @EJB
    protected void setAnwendungsLogikBeanALongJourney(AnwendungsLogikBeanALongJourney anwendungsLogikBeanALongJourney) {
        this.anwendungsLogikBean = anwendungsLogikBeanALongJourney;
    }

    @OnOpen
    public void open(@PathParam("username") String username, Session session) {
        connectedSessionsBean.addLongJourneySession(session);
        super.open(username, session);
    }

    @OnClose
    public void close(Session session) {
        connectedSessionsBean.removeLongJourneySession(session);
        super.close(session);
    }
}
