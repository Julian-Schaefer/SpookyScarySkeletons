package SpookyScarySkeletons.anwendungslogik;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import SpookyScarySkeletons.persistenzlogik.AccountManagementLocal;

@Singleton
@Startup
public class ScenarioManagement implements ScenarioManagementLocal {

    @EJB
    private AccountManagementLocal accountManagement;

    @PostConstruct
    private void init() {
        System.out.println("Initialized ScenarioManagement Bean!");
        testBeanDependency();
    }

    public void testBeanDependency() {
        System.out.println(accountManagement.sayHello());
    }

    @Override
    public int getNextStep(int currentStep) {
        return currentStep+1;
    }
}
