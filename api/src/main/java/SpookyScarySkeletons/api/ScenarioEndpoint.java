package SpookyScarySkeletons.api;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBean;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.api.dto.ChoiceDTO;
import SpookyScarySkeletons.api.dto.MessageDTO;
import SpookyScarySkeletons.api.model.Response;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.json.bind.JsonbBuilder;
import javax.websocket.*;
import java.io.IOException;

@Stateful
public abstract class ScenarioEndpoint implements AnwendungsLogikBean.AnwendungslogikListener {

    protected AnwendungsLogikBean anwendungsLogikBean;

    @EJB
    protected ConnectedSessionsBean connectedSessionsBean;

    @EJB
    protected DTOMapperBean dtoMapperBean;

    private Session session;

    public void open(String username, Session session) {
        this.session = session;

        anwendungsLogikBean.setUsername(username);
        anwendungsLogikBean.setAnwendungslogikListener(this);

        Message message = anwendungsLogikBean.getFirstMessage();
        MessageDTO messageDTO = dtoMapperBean.map(message, MessageDTO.class);
        sendMessage(Response.message(messageDTO));
    }

    public void close(Session session) {
        anwendungsLogikBean.dispose();
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
    public void onGameOver() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(-1);
        messageDTO.setContent("Game Over!");
        sendMessage(Response.message(messageDTO));
    }

    @Override
    public void onValueChanged(String username, int newValue) {
        sendMessage(Response.valueChanged(newValue));
        System.out.println("Sending new value to Username: " + username + ", " + newValue);
    }

    protected <T> void sendMessage(Response<T> response) {
        try {
            String replyMessageJSON = JsonbBuilder.create().toJson(response);
            session.getBasicRemote().sendText(replyMessageJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}