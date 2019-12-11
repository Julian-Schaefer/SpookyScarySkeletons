package SpookyScarySkeletons.api;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

// URL zum Testen: ws://localhost:8080/api/websocket

@ServerEndpoint("/websocket")
public class WebSocketEndpoint {

    @OnOpen
    public void open(Session session) {
        System.out.println("Open");
    }

    @OnClose
    public void close(Session session) {
        System.out.println("close");
    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("error");
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        System.out.println("handle" + message);
    }
}
