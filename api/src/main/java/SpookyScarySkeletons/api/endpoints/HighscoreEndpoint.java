package SpookyScarySkeletons.api.endpoints;

import SpookyScarySkeletons.persistenzlogik.AccountServiceLocal;
import SpookyScarySkeletons.persistenzlogik.model.Score;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/scores")
@Produces({ "application/json" })
public class HighscoreEndpoint {

    @EJB
    AccountServiceLocal accountService;

    @GET
    @Path("/highscore")
    public List<Score> getHighScores() {
        List<Score> scores = accountService.getAllHighScores();
        return scores;
    }

    @GET
    @Path("/{username}")
    public List<Score> getScoresForUser(@PathParam("username") String username) {
        List<Score> scores = accountService.getScoresForUsername(username);
        return scores;
    }
}
