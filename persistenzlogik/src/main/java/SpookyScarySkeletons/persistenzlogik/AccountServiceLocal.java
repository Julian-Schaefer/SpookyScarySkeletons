package SpookyScarySkeletons.persistenzlogik;

import SpookyScarySkeletons.persistenzlogik.model.Account;

import javax.ejb.Local;
import java.util.List;

@Local
public interface AccountServiceLocal {

    Account createAccount(Account account);
    List<Account> getAllAccounts();
    boolean checkUsername(String username);

}
