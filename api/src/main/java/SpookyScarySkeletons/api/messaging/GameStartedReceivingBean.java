package SpookyScarySkeletons.api.messaging;

import SpookyScarySkeletons.api.endpoints.ConnectedSessionsBean;
import SpookyScarySkeletons.api.model.Response;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.json.bind.JsonbBuilder;
import javax.websocket.Session;

@MessageDriven(mappedName = "Game Started Topic", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="java:app/jms/gameStartedTopic"),
})
public class GameStartedReceivingBean implements MessageListener {

    private static final String USERNAME = "username";
    private static final String SCENARIO = "scenario";

    @EJB
    private ConnectedSessionsBean connectedSessionsBean;

    @Override
    public void onMessage(Message message) {
        try{
            String username = message.getStringProperty(USERNAME);
            String scenario = message.getStringProperty(SCENARIO);
            String information = "Benutzer " + username + " hat gerade ein neues Spiel im Szenario \"" + scenario +"\" gestartet!";
            String messageJSON = JsonbBuilder.create().toJson(Response.information(information));

            connectedSessionsBean.sendToAllSessionsExceptOriginator(username, messageJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
