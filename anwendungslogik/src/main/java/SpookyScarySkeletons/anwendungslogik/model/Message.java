package SpookyScarySkeletons.anwendungslogik.model;

public class Message {

    private int id;
    private String content;
    private Choice firstChoice;
    private Choice secondChoice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Choice getFirstChoice() {
        return firstChoice;
    }

    public void setFirstChoice(Choice firstChoice) {
        this.firstChoice = firstChoice;
    }

    public Choice getSecondChoice() {
        return secondChoice;
    }

    public void setSecondChoice(Choice secondChoice) {
        this.secondChoice = secondChoice;
    }
}

