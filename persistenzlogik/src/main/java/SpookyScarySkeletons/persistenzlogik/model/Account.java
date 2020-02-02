package SpookyScarySkeletons.persistenzlogik.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {

    @Id
    private String username;

    @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Highscore> highscoreList = new ArrayList<Highscore>();
//    private double highscore = 100;
//
    public List<Highscore> getHighscoreList() {
        return highscoreList;
    }

    public void setHighscoreList(List<Highscore> highscoreList) {
        this.highscoreList = highscoreList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
