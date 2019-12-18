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
        Message message = new Message();
        message.setContent("Hallo");
        String choiceJSON = JsonbBuilder.create().toJson(message);

        session.getBasicRemote().sendText(choiceJSON);
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
        //String choiceJSON = JsonbBuilder.create().toJson(anwendungsLogikBean.getNextChoice());
        session.getBasicRemote().sendText("test");
    }
}
