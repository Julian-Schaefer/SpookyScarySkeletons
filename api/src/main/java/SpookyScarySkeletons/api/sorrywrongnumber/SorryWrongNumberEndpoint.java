package SpookyScarySkeletons.api.sorrywrongnumber;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBeanALongJourney;
import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBeanSorryWrongNumber;
import SpookyScarySkeletons.anwendungslogik.ScenarioManagement;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.api.ConnectedSessionsBean;
import SpookyScarySkeletons.api.DTOMapperBean;
import SpookyScarySkeletons.api.ScenarioEndpoint;
import SpookyScarySkeletons.api.dto.ChoiceDTO;
import SpookyScarySkeletons.api.dto.MessageDTO;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.json.bind.JsonbBuilder;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

// URL zum Testen: ws://localhost:8080/api/websocket

@Stateful
@ServerEndpoint(ScenarioManagement.ENDPOINT_SORRY_WRONG_NUMBER + "/{username}")
public class SorryWrongNumberEndpoint extends ScenarioEndpoint {

    @EJB
    protected void setAnwendungsLogikBeanSorryWrongNumber(AnwendungsLogikBeanSorryWrongNumber anwendungsLogikBeanSorryWrongNumber) {
        this.anwendungsLogikBean = anwendungsLogikBeanSorryWrongNumber;
    }

    @OnOpen
    public void open(@PathParam("username") String username, Session session) {
        connectedSessionsBean.addWrongNumberSession(session);
        super.open(username, session);
    }

    @OnClose
    public void close(Session session) {
        connectedSessionsBean.removeWrongNumberSession(session);
        super.close(session);
    }
}
