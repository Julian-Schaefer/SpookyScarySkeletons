package SpookyScarySkeletons.persistenzlogik;

import SpookyScarySkeletons.persistenzlogik.model.Account;
import SpookyScarySkeletons.persistenzlogik.model.Score;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AccountServiceLocal {

    Account createAccount(Account account);
    List<Account> getAllAccounts();
    boolean checkUsername(String username);
    Score addScore(Score score);
    List<Score> getAllHighScores();
    List<Score> getScoresForUsername(String username);

}
