package SpookyScarySkeletons.persistenzlogik.model;

        import javax.persistence.*;
        import java.util.Date;

@Entity
public class Highscore {

    @Id
    @GeneratedValue
    private Long ID;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userId")
    private Account userId;

    private String scenario;

    private double zeit;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Account getUserId() {
        return userId;
    }

    public void setUser(Account username) {
        this.userId = userId;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public double getZeit() {
        return zeit;
    }

    public void setZeit(double zeit) {
        this.zeit = zeit;
    }

}
