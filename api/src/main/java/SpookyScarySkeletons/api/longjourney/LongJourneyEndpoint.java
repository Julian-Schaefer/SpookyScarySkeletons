package SpookyScarySkeletons.api.longjourney;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBean;
import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBeanALongJourney;
import SpookyScarySkeletons.anwendungslogik.ScenarioManagement;
import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.api.ConnectedSessionsBean;
import SpookyScarySkeletons.api.DTOMapperBean;
import SpookyScarySkeletons.api.ScenarioEndpoint;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.json.bind.JsonbBuilder;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

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
