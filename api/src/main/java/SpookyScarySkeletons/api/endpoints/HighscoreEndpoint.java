package SpookyScarySkeletons.api.endpoints;

import SpookyScarySkeletons.anwendungslogik.AccountManagement;
import SpookyScarySkeletons.persistenzlogik.model.Account;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/highscore")
@Produces({ "application/json" })
public class HighscoreEndpoint {

    @EJB
    AccountManagement accountManagement;

    @GET
    public List<Account> getHighscores() {
        List<Account> accounts = accountManagement.getAllAccounts();
//        accounts.sort(Comparator.comparingDouble(Account::getHighscore).reversed());
        return accounts;
    }
}
