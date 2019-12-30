package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.ScenarioEndpoint;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ScenarioManagementLocal {

    List<ScenarioEndpoint> getScenarioEndpoints();

}
