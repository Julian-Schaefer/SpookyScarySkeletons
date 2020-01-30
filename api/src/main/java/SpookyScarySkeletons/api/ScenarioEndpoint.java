package SpookyScarySkeletons.api;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBean;
import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBeanSorryWrongNumber;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.api.dto.ChoiceDTO;
import SpookyScarySkeletons.api.dto.MessageDTO;
import SpookyScarySkeletons.api.model.Response;

import javax.ejb.EJB;
import javax.json.bind.JsonbBuilder;
import javax.websocket.*;
import java.io.IOException;

public abstract class ScenarioEndpoint {

    protected AnwendungsLogikBean anwendungsLogikBean;

    @EJB
    protected ConnectedSessionsBean connectedSessionsBean;

    @EJB
    protected DTOMapperBean dtoMapperBean;

    private Session session;

    public void open(String username, Session session) {
        this.session = session;

        anwendungsLogikBean.setUsername(username);
        anwendungsLogikBean.setValueChangeListener(this::onValueChanged);

        Message message = anwendungsLogikBean.getFirstMessage();
        MessageDTO messageDTO = dtoMapperBean.map(message, MessageDTO.class);
        sendMessage(messageDTO);
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

        Message replyMessage = anwendungsLogikBean.getNextMessage(choiceDTO.getId());
        MessageDTO replyMessageDTO = dtoMapperBean.map(replyMessage, MessageDTO.class);
        sendMessage(replyMessageDTO);
    }

    public void onValueChanged(String username, int newValue) {
        sendMessage(newValue);
        System.out.println("Sending new value to Username: " + username + ", " + newValue);
    }

    protected <T> void sendMessage(T content) {
        try {
            String replyMessageJSON = JsonbBuilder.create().toJson(new Response<>(Response.Type.VALUE_CHANGE, content));
            session.getBasicRemote().sendText(replyMessageJSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}