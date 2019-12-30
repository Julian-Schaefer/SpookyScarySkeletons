package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.persistenzlogik.AccountServiceLocal;
import SpookyScarySkeletons.persistenzlogik.model.Account;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AccountManagement {

    @EJB
    private AccountServiceLocal accountService;

    public Account createAccount(Account account) {
        Account createdAccount = accountService.createAccount(account);
        System.out.println("Created Account: " + createdAccount.getUsername());
        return createdAccount;
    }

}
