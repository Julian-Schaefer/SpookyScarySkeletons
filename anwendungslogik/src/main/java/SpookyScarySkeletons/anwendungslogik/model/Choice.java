package SpookyScarySkeletons.anwendungslogik.model;

import java.io.Serializable;


public class Choice implements Serializable {

    public enum Type {
        NORMAL,
        WON,
        LOST
    }

    private int id;
    private String content;
    private Message nextMessage;
    private int valueChange;
    private int minValue;
    private Type type;

    public Choice() {
    }

    public Choice(int id, String content, Message nextMessage, int valueChange, int minValue, Type type) {
        this.id = id;
        this.content = content;
        this.nextMessage = nextMessage;
        this.valueChange = valueChange;
        this.minValue = minValue;
        this.type = type;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof Choice) {
            Choice choice = (Choice) obj;
            if(choice.getId() == id && choice.getContent().equals(content) &&
                    choice.getNextMessage().equals(nextMessage) &&
                    choice.getValueChange() ==valueChange &&
                    choice.getMinValue() == minValue &&
                    choice.getType() == type) {
                return true;
            }
        }

        return false;
    }
}
