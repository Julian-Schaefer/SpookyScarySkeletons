package SpookyScarySkeletons.api;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBean;
import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;

import javax.ejb.*;
import javax.json.bind.JsonbBuilder;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

// URL zum Testen: ws://localhost:8080/api/websocket

@Stateful
@ServerEndpoint("/websocket")
public class WebSocketEndpoint {

    @EJB
    private AnwendungsLogikBean anwendungsLogikBean;

    @EJB
    private ConnectedSessionsBean connectedSessionsBean;

    @OnOpen
    public void open(Session session) throws IOException {
        connectedSessionsBean.addSession(session);
        Choice firstChoice = new Choice();
        firstChoice.setContent("Antwort 1");

        Choice secondChoice = new Choice();
        secondChoice.setContent("Antwort 2");

        Message message = new Message();
        message.setContent("Hallo");
        message.setFirstChoice(firstChoice);
        message.setSecondChoice(secondChoice);
        String messageJSON = JsonbBuilder.create().toJson(message);

        session.getBasicRemote().sendText(messageJSON);
    }

    @OnClose
    public void close(Session session) {
        connectedSessionsBean.removeSession(session);
        anwendungsLogikBean.dispose();
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        Choice firstChoice = new Choice();
        firstChoice.setContent("Antwort 1");

        Choice secondChoice = new Choice();
        secondChoice.setContent("Antwort 2");

        Message replyMessage = new Message();
        replyMessage.setContent("Hallo");
        replyMessage.setFirstChoice(firstChoice);
        replyMessage.setSecondChoice(secondChoice);
        session.getBasicRemote().sendText("test");
        String replyMessageJSON = JsonbBuilder.create().toJson(replyMessage);

        session.getBasicRemote().sendText(replyMessageJSON);
    }
}
