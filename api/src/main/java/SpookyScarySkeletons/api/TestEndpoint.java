package SpookyScarySkeletons.api;

import SpookyScarySkeletons.anwendungslogik.ScenarioManagementLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

// URL zum Testen: http://localhost:8080/api/helloworld

@Path("/")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@Stateless
public class TestEndpoint {

    @EJB
    private ScenarioManagementLocal scenarioManagement;

    @GET
    @Path("helloworld")
    public Response getHelloWorld() {
        return Response.ok().entity("Hello World!").build();
    }

    @GET
    @Path("nextstep/{step}")
    public Response getNextStep(@PathParam("step") int currentStep) {
        return Response.ok().entity("Next Step: " + scenarioManagement.getNextStep(currentStep)).build();
    }
}
