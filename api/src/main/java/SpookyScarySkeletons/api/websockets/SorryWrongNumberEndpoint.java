package SpookyScarySkeletons.api.websockets;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBeanSorryWrongNumber;
import SpookyScarySkeletons.anwendungslogik.ScenarioManagement;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

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
    @Remove
    public void close(Session session) {
        connectedSessionsBean.removeWrongNumberSession(session);
        super.dispose();
    }
}
