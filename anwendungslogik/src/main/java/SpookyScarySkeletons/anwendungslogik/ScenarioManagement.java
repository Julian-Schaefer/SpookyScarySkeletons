package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.ScenarioEndpoint;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class ScenarioManagement implements ScenarioManagementLocal {

    public static final String ENDPOINT_SORRY_WRONG_NUMBER = "/sorryWrongNumber";
    public static final String ENDPOINT_LONG_JOURNEY = "/longJourney";

    @PostConstruct
    private void init() {
        System.out.println("Initialized ScenarioManagement Bean!");
    }

    @Override
    public List<ScenarioEndpoint> getScenarioEndpoints() {
        List<ScenarioEndpoint> scenarioEndpoints = new LinkedList<>();

        ScenarioEndpoint sorryWrongNumberEndpoint = new ScenarioEndpoint();
        sorryWrongNumberEndpoint.setName("Sorry, wrong number.");
        sorryWrongNumberEndpoint.setWebsocketEndpoint("/api/" + ENDPOINT_SORRY_WRONG_NUMBER);
        scenarioEndpoints.add(sorryWrongNumberEndpoint);

        ScenarioEndpoint longJourneyEndpoint = new ScenarioEndpoint();
        longJourneyEndpoint.setName("A long journey.");
        longJourneyEndpoint.setWebsocketEndpoint("/api/" + ENDPOINT_LONG_JOURNEY);
        scenarioEndpoints.add(longJourneyEndpoint);
        return scenarioEndpoints;
    }
}
