package SpookyScarySkeletons.api.endpoints;

import SpookyScarySkeletons.anwendungslogik.ScenarioManagement;
import SpookyScarySkeletons.anwendungslogik.ScenarioManagementLocal;
import SpookyScarySkeletons.anwendungslogik.model.ScenarioEndpoint;

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

    @EJB
    private ScenarioManagementLocal scenarioManagement;

    @GET
    public Response getAvailableScenarios() {
        return Response.ok(scenarioManagement.getScenarioEndpoints()).build();
    }
}
