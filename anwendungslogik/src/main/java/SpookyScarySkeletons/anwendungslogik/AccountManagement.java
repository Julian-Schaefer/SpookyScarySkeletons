package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.persistenzlogik.AccountServiceLocal;
import SpookyScarySkeletons.persistenzlogik.model.Account;
import SpookyScarySkeletons.persistenzlogik.model.AccountAlreadyExistsException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class AccountManagement {

    @EJB
    private AccountServiceLocal accountService;

    public Account createAccount(Account account) throws AccountAlreadyExistsException {
        Account createdAccount = accountService.createAccount(account);
        System.out.println("Created Account: " + createdAccount.getUsername());
        return createdAccount;
    }

    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}
