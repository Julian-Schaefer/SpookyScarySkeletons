package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Scenario;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ScenarioManagementLocal {

    List<Scenario> getScenarios();

}
