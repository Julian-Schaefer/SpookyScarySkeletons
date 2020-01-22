package SpookyScarySkeletons.persistenzlogik;

import SpookyScarySkeletons.persistenzlogik.model.Account;
import SpookyScarySkeletons.persistenzlogik.model.AccountAlreadyExistsException;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.LinkedList;
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
//        entityManager.flush();
        return account;
    }

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new LinkedList<>();
        Query query = entityManager.createQuery("SELECT '*' FROM Account");
        return accounts;
    }

    @Override
    public boolean checkUsername(String username) {
        return entityManager.find(Account.class, username) != null;
    }
}
