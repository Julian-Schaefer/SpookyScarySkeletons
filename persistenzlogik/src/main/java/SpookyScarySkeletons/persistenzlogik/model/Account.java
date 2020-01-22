package SpookyScarySkeletons.persistenzlogik.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    private String username;
//    private double highscore = 100;
//
//    public double getHighscore() {
//        return highscore;
//    }
//
//    public void setHighscore(double highscore) {
//        this.highscore = highscore;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
