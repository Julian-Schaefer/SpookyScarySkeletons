package SpookyScarySkeletons.api;

import SpookyScarySkeletons.anwendungslogik.TestStatefulBean;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

// URL zum Testen: ws://localhost:8080/api/websocket

@Stateful
@ServerEndpoint("/websocket")
public class WebSocketEndpoint {

    @EJB
    private TestStatefulBean testStatefulBean;

    @EJB
    private ConnectedSessionsBean connectedSessionsBean;

    @OnOpen
    public void open(Session session) {
        connectedSessionsBean.addSession(session);
    }

    @OnClose
    public void close(Session session) {
        connectedSessionsBean.removeSession(session);
        testStatefulBean.dispose();
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText("Server: " + message + ", " + testStatefulBean.getNumber());
    }

  /*  @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Received Message" + message.getDoubleProperty("asd"));
        } catch(JMSException e) {
            e.printStackTrace();
        }
    }*/
}
