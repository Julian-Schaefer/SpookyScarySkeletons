package SpookyScarySkeletons.persistenzlogik;

import SpookyScarySkeletons.persistenzlogik.model.Account;
import SpookyScarySkeletons.persistenzlogik.model.AccountAlreadyExistsException;
import SpookyScarySkeletons.persistenzlogik.model.Score;

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
        entityManager.createQuery("DELETE FROM Score").executeUpdate();
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
    public Score addScore(Score score) {
        entityManager.persist(score);
        return score;
    }

    @Override
    public List<Score> getAllHighScores() {
        Query query = entityManager.createQuery("SELECT a FROM Score a ORDER BY a.duration ASC");
        List<Score> highScores = (List<Score>) query.getResultList();
        return highScores;
    }

    @Override
    public List<Score> getScoresForUsername(String username) {
        Query query = entityManager.createQuery("SELECT a FROM Score a WHERE a.username = username");
        List<Score> scores = (List<Score>) query.getResultList();
        return scores;
    }
}
