package SpookyScarySkeletons.api.messaging;

import SpookyScarySkeletons.api.ConnectedSessionsBean;
import SpookyScarySkeletons.api.model.Response;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.json.bind.JsonbBuilder;

@MessageDriven(mappedName = "Game Over Topic", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName="destination", propertyValue="java:app/jms/gameOverTopic"),
})
public class GameOverReceivingBean implements MessageListener {

    private static final String USERNAME = "username";
    private static final String SCENARIO = "scenario";
    private static final String MINUTES = "minutes";
    private static final String SECONDS = "seconds";

    @EJB
    private ConnectedSessionsBean connectedSessionsBean;

    @Override
    public void onMessage(Message message) {
        try{
            String username = message.getStringProperty(USERNAME);
            String scenario = message.getStringProperty(SCENARIO);
            int minutes = message.getIntProperty(MINUTES);
            int seconds = message.getIntProperty(SECONDS);
            String information = "Benutzer " + username + " hat gerade das Szenario \"" + scenario + "\" in " + minutes
                    + " Minuten und " + seconds + " Sekunden beendet!";
            String messageJSON = JsonbBuilder.create().toJson(Response.information(information));

            connectedSessionsBean.sendToAllSessionsExceptOriginator(username, messageJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
