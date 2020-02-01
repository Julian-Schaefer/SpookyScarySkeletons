package SpookyScarySkeletons.api.websockets;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBean;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.api.endpoints.ConnectedSessionsBean;
import SpookyScarySkeletons.api.dto.ChoiceDTO;
import SpookyScarySkeletons.api.dto.MessageDTO;
import SpookyScarySkeletons.api.model.Response;
import SpookyScarySkeletons.api.util.DTOMapperBean;

import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.json.bind.JsonbBuilder;
import javax.websocket.*;
import java.io.IOException;

public abstract class ScenarioEndpoint implements AnwendungsLogikBean.AnwendungslogikListener {

    private static final String USERNAME = "username";

    protected AnwendungsLogikBean anwendungsLogikBean;

    @EJB
    protected ConnectedSessionsBean connectedSessionsBean;

    @EJB
    protected DTOMapperBean dtoMapperBean;

    private Session session;

    public void open(String username, Session session) {
        this.session = session;
        session.getUserProperties().put(USERNAME, username);

        anwendungsLogikBean.startGame(username);
        anwendungsLogikBean.setAnwendungslogikListener(this);

        Message message = anwendungsLogikBean.getFirstMessage();
        MessageDTO messageDTO = dtoMapperBean.map(message, MessageDTO.class);
        sendMessage(Response.message(messageDTO));
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        ChoiceDTO choiceDTO = ChoiceDTO.getChoiceDTOFromJSONString(message);
        anwendungsLogikBean.getNextMessage(choiceDTO.getId());
    }

    @Override
    public void onNewMessage(Message message) {
        MessageDTO messageDTO = dtoMapperBean.map(message, MessageDTO.class);
        sendMessage(Response.message(messageDTO));
    }

    @Override
    public void onGameOver(String message) {
        System.out.println("Game Over!");
        sendMessage(Response.gameOver(message));
    }

    @Override
    public void onValueChanged(String username, int newValue) {
        sendMessage(Response.valueChanged(newValue));
        System.out.println("Sending new value to Username: " + username + ", " + newValue);
    }

    @Remove
    public void dispose() {
        System.out.println("Closing Scenario Endpoint");
        anwendungsLogikBean.dispose();
    }

    @PreDestroy
    public void onDestroy() {
        System.out.println("ScenarioEndpoint gets destroyed");
    }

    protected <T> void sendMessage(Response<T> response) {
        try {
            if(session.isOpen()) {
                String replyMessageJSON = JsonbBuilder.create().toJson(response);
                session.getBasicRemote().sendText(replyMessageJSON);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}