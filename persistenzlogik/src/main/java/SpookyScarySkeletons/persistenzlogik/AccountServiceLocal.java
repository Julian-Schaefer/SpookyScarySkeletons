package SpookyScarySkeletons.persistenzlogik;

import SpookyScarySkeletons.persistenzlogik.model.Account;

import javax.ejb.Local;

@Local
public interface AccountServiceLocal {

    Account createAccount(Account account);
    boolean checkUsername(String username);

}
