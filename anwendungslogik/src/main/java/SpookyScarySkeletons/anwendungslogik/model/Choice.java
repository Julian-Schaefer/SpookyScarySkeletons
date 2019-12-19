package SpookyScarySkeletons.anwendungslogik.model;

public class Choice {

    private int id;
    private String content;
    private Message nextMessage;
    private int value;

    public Choice() {
    }

    public Choice(int id, String content, Message nextMessage, int value) {
        this.id = id;
        this.content = content;
        this.nextMessage = nextMessage;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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

    public Message getNextMessage() {
        return nextMessage;
    }

    public void setNextMessage(Message nextMessage) {
        this.nextMessage = nextMessage;
    }
}
