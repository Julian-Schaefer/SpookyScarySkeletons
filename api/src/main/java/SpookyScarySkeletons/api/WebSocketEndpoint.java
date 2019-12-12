package SpookyScarySkeletons.api;

import javax.annotation.PostConstruct;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.Stateful;
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

@MessageDriven(mappedName = "Statistic Topic", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="java:app/jms/statisticTopic"),
})
@ServerEndpoint("/websocket")
public class WebSocketEndpoint implements MessageListener {

    private List<Session> sessions;

    @PostConstruct
    private void init() {
        sessions = new LinkedList<>();
    }

    @OnOpen
    public void open(Session session) {
        sessions.add(session);
    }

    @OnClose
    public void close(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("error");
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        session.getBasicRemote().sendText("Server: " + message);
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("Received Message" + message.getDoubleProperty("asd"));
        } catch(JMSException e) {
            e.printStackTrace();
        }
    }
}
