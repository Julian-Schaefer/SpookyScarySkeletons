package SpookyScarySkeletons.api.sorrywrongnumber;

import SpookyScarySkeletons.anwendungslogik.AnwendungsLogikBeanSorryWrongNumber;
import SpookyScarySkeletons.anwendungslogik.ScenarioManagement;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.api.ConnectedSessionsBean;
import SpookyScarySkeletons.api.DTOMapperBean;
import SpookyScarySkeletons.api.dto.ChoiceDTO;
import SpookyScarySkeletons.api.dto.MessageDTO;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.json.bind.JsonbBuilder;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

// URL zum Testen: ws://localhost:8080/api/websocket

@Stateful
@ServerEndpoint(ScenarioManagement.ENDPOINT_SORRY_WRONG_NUMBER)
public class SorryWrongNumberEndpoint {

    @EJB
    private AnwendungsLogikBeanSorryWrongNumber anwendungsLogikBean;

    @EJB
    private ConnectedSessionsBean connectedSessionsBean;

    @EJB
    private DTOMapperBean dtoMapperBean;

    @OnOpen
    public void open(Session session) throws IOException {
        connectedSessionsBean.addWrongNumberSession(session);

        Message message = anwendungsLogikBean.getFirstMessage();
        MessageDTO messageDTO = dtoMapperBean.map(message, MessageDTO.class);
        String messageJSON = JsonbBuilder.create().toJson(messageDTO);

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
        ChoiceDTO choiceDTO = ChoiceDTO.getChoiceDTOFromJSONString(message);

        Message replyMessage = anwendungsLogikBean.getNextMessage(choiceDTO.getId());
        MessageDTO replyMessageDTO = dtoMapperBean.map(replyMessage, MessageDTO.class);
        String replyMessageJSON = JsonbBuilder.create().toJson(replyMessageDTO, MessageDTO.class);

        session.getBasicRemote().sendText(replyMessageJSON);
    }
}
