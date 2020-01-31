package SpookyScarySkeletons.anwendungslogik.model;

import java.io.Serializable;

public class Message implements Serializable {

    private int id;
    private String content;
    private Choice firstChoice;
    private Choice secondChoice;

    public Message() {
    }

    public Message(int id, String content, Choice firstChoice, Choice secondChoice) {
        this.id = id;
        this.content = content;
        this.firstChoice = firstChoice;
        this.secondChoice = secondChoice;
    }

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

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Message) {
            Message message = (Message) obj;
            if(message.getId() == id && message.getContent().equals(content) &&
                    (message.getFirstChoice() != null && message.getFirstChoice().equals(firstChoice)) &&
                    (message.getSecondChoice() != null && message.getSecondChoice().equals(secondChoice))) {
                return true;
            }
        }

        return false;
    }
}

