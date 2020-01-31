package SpookyScarySkeletons.api.endpoints;

import SpookyScarySkeletons.anwendungslogik.AccountManagement;
import SpookyScarySkeletons.persistenzlogik.model.Account;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/account")
@Consumes({ "application/json" })
@Produces({ "application/json" })
@Stateless
public class AccountEndpoint {

    @EJB
    private AccountManagement accountManagement;

    @POST
    public Response createUser(Account account) {
        // Emulate Latency
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Account createdAccount = accountManagement.createAccount(account);
            return Response.ok(createdAccount).build();
        } catch (RuntimeException e) {
            // Account already exists
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
