package SpookyScarySkeletons.api.endpoints;

import SpookyScarySkeletons.persistenzlogik.AccountServiceLocal;
import SpookyScarySkeletons.persistenzlogik.model.Account;
import SpookyScarySkeletons.persistenzlogik.model.Highscore;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Comparator;
import java.util.List;

@Path("/highscore")
@Produces({ "application/json" })
public class HighscoreEndpoint {

    @EJB
    AccountServiceLocal accountService;

    @GET
    public List<Highscore> getHighscores() {
        List<Highscore> highscores = accountService.getAllHighscores();
 //       highscores.sort(Comparator.comparingDouble(Highscore::getZeit).reversed());
        return highscores;
    }
}
