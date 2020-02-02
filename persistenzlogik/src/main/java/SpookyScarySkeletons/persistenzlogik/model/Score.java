package SpookyScarySkeletons.persistenzlogik.model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Score {

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String scenario;
    private long duration;
    private String durationString;
    private String dateTime;

    public Score() {
    }

    public Score(String username, String scenario, long duration) {
        this.username = username;
        this.scenario = scenario;
        this.duration = duration;
        this.durationString = getMinutes() + ":" + getSeconds() + " min";

        DateFormat dateFormat = new SimpleDateFormat("DD/MM/YYYY HH:mm:ss");
        Date date = new Date();
        this.dateTime = dateFormat.format(date);
    }

    public int getMinutes() {
        return getMinutesAndSeconds()[0];
    }

    public int getSeconds() {
        return getMinutesAndSeconds()[1];
    }

    private int[] getMinutesAndSeconds() {
        int minutes = (int) (this.duration/1000.0/60.0);
        double lastMinute = (this.duration/1000.0/60.0) % 1;
        int seconds = (int) (lastMinute * 60.0);

        return new int[]{ minutes, seconds };
    }

    public Long getID() {
        return id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUser(String username) {
        this.username = username;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDurationString() {
        return durationString;
    }

    public void setDurationString(String durationString) {
        this.durationString = durationString;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
