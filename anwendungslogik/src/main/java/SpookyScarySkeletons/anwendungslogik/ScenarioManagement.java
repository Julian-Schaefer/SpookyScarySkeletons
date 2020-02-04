package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Scenario;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class ScenarioManagement implements ScenarioManagementLocal {

    public static final String NAME_SORRY_WRONG_NUMBER = "Sorry, wrong number.";
    public static final String NAME_LONG_JOURNEY = "A long journey.";
    public static final String ENDPOINT_SORRY_WRONG_NUMBER = "/sorryWrongNumber";
    public static final String ENDPOINT_LONG_JOURNEY = "/longJourney";

    @PostConstruct
    private void init() {
        System.out.println("Initialized ScenarioManagement Bean!");
    }

    @Override
    public List<Scenario> getScenarios() {
        List<Scenario> scenarios = new LinkedList<>();

        Scenario sorryWrongNumberScenario = new Scenario();
        sorryWrongNumberScenario.setName(NAME_SORRY_WRONG_NUMBER);
        sorryWrongNumberScenario.setWebsocketEndpoint("/api" + ENDPOINT_SORRY_WRONG_NUMBER);
        sorryWrongNumberScenario.setPreviewImageUrl("/frontend/images/sorrywrongnumber_card_bg.png");
        sorryWrongNumberScenario.setBackgroundImageUrl("/frontend/images/sorrywrongnumber_chat_bg.jpg");
        scenarios.add(sorryWrongNumberScenario);

        Scenario longJourneyScenario = new Scenario();
        longJourneyScenario.setName(NAME_LONG_JOURNEY);
        longJourneyScenario.setWebsocketEndpoint("/api" + ENDPOINT_LONG_JOURNEY);
        longJourneyScenario.setPreviewImageUrl("/frontend/images/alongjourney_card_bg.png");
        longJourneyScenario.setBackgroundImageUrl("/frontend/images/alongjourney_chat_bg.jpg");
        scenarios.add(longJourneyScenario);
        return scenarios;
    }
}
