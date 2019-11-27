package SpookyScarySkeletons.anwendungslogik;

import javax.ejb.Local;

@Local
public interface ScenarioManagementLocal {

    int getNextStep(int currentStep);

}
