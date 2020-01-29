package SpookyScarySkeletons.api;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.api.model.Response;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.json.bind.JsonbBuilder;
import javax.websocket.Session;

@MessageDriven(mappedName = "Statistic Topic", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="java:app/jms/statisticTopic"),
})
public class ReceivingBean implements MessageListener {

    @EJB
    private ConnectedSessionsBean connectedSessionsBean;

    @Override
    public void onMessage(Message message) {
        try{
            String receivedMessage = "Received Message" + message.getDoubleProperty("asd");
            String replyMessageJSON = JsonbBuilder.create().toJson(new Response<>(Response.Type.INFORMATION, receivedMessage));

            for(Session session: connectedSessionsBean.getAllSessions()) {
                System.out.println("Sending to session: " + session.getId());
                session.getBasicRemote().sendText(replyMessageJSON);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
