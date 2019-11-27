package SpookyScarySkeletons.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

// URL zum Testen: http://localhost:8080/api/helloworld

@Path("/helloworld")
@Consumes({ "application/json" })
@Produces({ "application/json" })
public class TestEndpoint {

    @GET
    public Response getHelloWorld() {
        return Response.ok().entity("Hello World!").build();
    }

}
