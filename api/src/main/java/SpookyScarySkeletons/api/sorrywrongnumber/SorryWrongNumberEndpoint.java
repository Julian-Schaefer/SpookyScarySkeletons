package SpookyScarySkeletons.api.sorrywrongnumber;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBean;
import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.api.AvailableScenariosEndpoint;
import SpookyScarySkeletons.api.ConnectedSessionsBean;

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
@ServerEndpoint(AvailableScenariosEndpoint.ENDPOINT_SORRY_WRONG_NUMBER)
public class SorryWrongNumberEndpoint {

    @EJB
    private AnwendungsLogikBean anwendungsLogikBean;

    @EJB
    private ConnectedSessionsBean connectedSessionsBean;

    @OnOpen
    public void open(Session session) throws IOException {
        connectedSessionsBean.addWrongNumberSession(session);
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
        connectedSessionsBean.removeWrongNumberSession(session);
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
        String replyMessageJSON = JsonbBuilder.create().toJson(replyMessage);

        session.getBasicRemote().sendText(replyMessageJSON);
    }
}
