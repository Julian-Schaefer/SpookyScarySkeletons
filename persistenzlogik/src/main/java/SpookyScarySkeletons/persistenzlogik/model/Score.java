package SpookyScarySkeletons.persistenzlogik.model;

public class Score {

    private String username;
    private String duration;
    private String dateTime;

    public Score() {
    }

    public Score(String username, String duration, String dateTime) {
        this.username = username;
        this.duration = duration;
        this.dateTime = dateTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
