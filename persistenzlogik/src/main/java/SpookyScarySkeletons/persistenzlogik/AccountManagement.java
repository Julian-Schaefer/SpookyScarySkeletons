package SpookyScarySkeletons.persistenzlogik;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class AccountManagement implements AccountManagementLocal {

    @PersistenceContext(unitName = "SpookyScarySkeletons")
    private EntityManager entityManager;

    @PostConstruct
    private void init() {
        System.out.println("Initialized AccountManagement Bean!");
        Account account = new Account();
        account.setUsername("testuser");
        entityManager.persist(account);
    }

    public String sayHello() {
        return "Hello from Account Management";
    }
}
