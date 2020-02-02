package SpookyScarySkeletons.api.endpoints;

import SpookyScarySkeletons.persistenzlogik.model.Score;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.LinkedList;
import java.util.List;

@Path("/scores")
@Produces({ "application/json" })
public class HighscoreEndpoint {

    @GET
    @Path("/highscore")
    public List<Score> getHighScores() {
        List<Score> scores = new LinkedList<>();
        scores.add(new Score("test", "2 min", "sad"));
        scores.add(new Score("test", "2 min", "asd"));
        scores.add(new Score("test", "2 min", "asd"));
        scores.add(new Score("test", "2 min", "asd"));
        return scores;
    }

    @GET
    @Path("/{username}")
    public List<Score> getScoresForUser(@PathParam("username") String username) {
        List<Score> scores = new LinkedList<>();
        scores.add(new Score("test", "2 min", "sad"));
        scores.add(new Score("sdasdtest", "2 min", "asd"));
        scores.add(new Score("test", "2 min", "asd"));
        scores.add(new Score("test", "2 min", "asd"));
        return scores;
    }
}
