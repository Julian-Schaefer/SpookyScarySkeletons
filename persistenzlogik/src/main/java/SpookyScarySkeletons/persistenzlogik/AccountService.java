package SpookyScarySkeletons.persistenzlogik;

import SpookyScarySkeletons.persistenzlogik.model.Account;
import SpookyScarySkeletons.persistenzlogik.model.AccountAlreadyExistsException;
import SpookyScarySkeletons.persistenzlogik.model.Highscore;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Singleton
@Startup
public class AccountService implements AccountServiceLocal {

    @PersistenceContext(unitName = "SpookyScarySkeletons")
    private EntityManager entityManager;

    @PostConstruct
    private void init() {
        System.out.println("Initialized AccountManagement Bean!");
    }

    @Override
    public Account createAccount(Account account) {
        if(checkUsername(account.getUsername())) {
            throw new AccountAlreadyExistsException();
        }

        entityManager.persist(account);
        entityManager.flush();
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        Query query = entityManager.createQuery("SELECT DISTINCT a FROM Account a");
        List<Account> accounts = (List<Account>) query.getResultList();
        return accounts;
    }

    @Override
    public boolean checkUsername(String username) {
        return entityManager.find(Account.class, username) != null;
    }

    @Override
    public Highscore addScore(String username, String scenario, double seconds) {
        Highscore score = new Highscore();
        score.setUser(entityManager.find(Account.class, username));
        score.setScenario(scenario);
        score.setZeit(seconds);
        entityManager.persist(score);

        return score;
    }

    @Override
    public List<Highscore> getAllHighscores() {
        Query query = entityManager.createQuery("SELECT a FROM Highscore a");
        List<Highscore> highscores = (List<Highscore>) query.getResultList();
        return highscores;
    }
}
