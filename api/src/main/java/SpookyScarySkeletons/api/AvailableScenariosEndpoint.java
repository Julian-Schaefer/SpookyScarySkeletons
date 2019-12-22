package SpookyScarySkeletons.api;

import SpookyScarySkeletons.anwendungslogik.ScenarioManagementLocal;
import SpookyScarySkeletons.api.model.ScenarioEndpoint;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

// URL zum Testen: http://localhost:8080/api/helloworld

@Path("/scenarios")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@Stateless
public class AvailableScenariosEndpoint {

    public static final String ENDPOINT_SORRY_WRONG_NUMBER = "/sorryWrongNumber";
    public static final String ENDPOINT_LONG_JOURNEY = "/longJourney";

    @GET
    public Response getAvailableScenarios() {
        List<ScenarioEndpoint> scenarioEndpoints = new LinkedList<>();

        ScenarioEndpoint sorryWrongNumberEndpoint = new ScenarioEndpoint();
        sorryWrongNumberEndpoint.setName("Sorry, wrong number.");
        sorryWrongNumberEndpoint.setWebsocketEndpoint("/api/" + ENDPOINT_SORRY_WRONG_NUMBER);
        scenarioEndpoints.add(sorryWrongNumberEndpoint);

        ScenarioEndpoint longJourneyEndpoint = new ScenarioEndpoint();
        longJourneyEndpoint.setName("A long journey.");
        longJourneyEndpoint.setWebsocketEndpoint("/api/" + ENDPOINT_LONG_JOURNEY);
        scenarioEndpoints.add(longJourneyEndpoint);

        return Response.ok(scenarioEndpoints).build();
    }
}
