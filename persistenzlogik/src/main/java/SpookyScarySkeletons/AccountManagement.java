package SpookyScarySkeletons;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class AccountManagement implements AccountManagementLocal {

    @PostConstruct
    private void init() {
        System.out.println("Initialized AccountManagement Bean!");
    }

}
