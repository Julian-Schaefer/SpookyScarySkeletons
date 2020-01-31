package SpookyScarySkeletons.anwendungslogik.model;

import java.io.Serializable;

public class Choice implements Serializable {

    private int id;
    private String content;
    private Message nextMessage;
    private int valueChange;
    private int minValue;

    public Choice() {
    }

    public Choice(int id, String content, Message nextMessage, int valueChange, int minValue) {
        this.id = id;
        this.content = content;
        this.nextMessage = nextMessage;
        this.valueChange = valueChange;
        this.minValue = minValue;
    }

    public int getValueChange() {
        return valueChange;
    }

    public void setValueChange(int valueChange) {
        this.valueChange = valueChange;
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

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Choice) {
            Choice choice = (Choice) obj;
            if(choice.getId() == id && choice.getContent().equals(content) &&
                    choice.getNextMessage().equals(nextMessage) &&
                    choice.getValueChange() ==valueChange &&
                    choice.getMinValue() == minValue) {
                return true;
            }
        }

        return false;
    }
}
