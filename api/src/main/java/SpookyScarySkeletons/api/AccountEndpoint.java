package SpookyScarySkeletons.api;

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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            Account createdAccount = accountManagement.createAccount(account);
            return Response.ok(createdAccount).build();
        } catch (RuntimeException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
