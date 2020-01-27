package SpookyScarySkeletons.api;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBean;
import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBeanSorryWrongNumber;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.api.dto.ChoiceDTO;
import SpookyScarySkeletons.api.dto.MessageDTO;

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

    public void open(String username, Session session) throws IOException {
        anwendungsLogikBean.setUsername(username);
        anwendungsLogikBean.setValueChangeListener(this::onValueChanged);

        Message message = anwendungsLogikBean.getFirstMessage();
        MessageDTO messageDTO = dtoMapperBean.map(message, MessageDTO.class);
        String messageJSON = JsonbBuilder.create().toJson(messageDTO);

        session.getBasicRemote().sendText(messageJSON);
    }

    public void close(Session session) {
        anwendungsLogikBean.dispose();
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        ChoiceDTO choiceDTO = ChoiceDTO.getChoiceDTOFromJSONString(message);

        Message replyMessage = anwendungsLogikBean.getNextMessage(choiceDTO.getId());
        MessageDTO replyMessageDTO = dtoMapperBean.map(replyMessage, MessageDTO.class);
        String replyMessageJSON = JsonbBuilder.create().toJson(replyMessageDTO, MessageDTO.class);

        session.getBasicRemote().sendText(replyMessageJSON);
    }

    public void onValueChanged(String username, int newValue) {
        //TODO
        System.out.println("Sending new value to Username: " + username + ", " + newValue);
    }
}