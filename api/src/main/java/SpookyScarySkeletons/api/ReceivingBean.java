package SpookyScarySkeletons.api;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
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
        for(Session session: connectedSessionsBean.getAllSessions()) {
            System.out.println("Sending to session: " + session.getId());
            try {
                session.getBasicRemote().sendText("Received Message" + message.getDoubleProperty("asd"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
