package SpookyScarySkeletons.persistenzlogik;

import SpookyScarySkeletons.persistenzlogik.model.Account;
import SpookyScarySkeletons.persistenzlogik.model.Highscore;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AccountServiceLocal {

    Account createAccount(Account account);
    List<Account> getAllAccounts();
    boolean checkUsername(String username);
    Highscore addScore(String username, String scenario, double seconds);
    List<Highscore> getAllHighscores();

}
