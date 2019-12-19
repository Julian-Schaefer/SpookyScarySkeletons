package SpookyScarySkeletons.anwendungslogik.xmlDtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class ChoiceDto {
    private int id;
    private String content;
    private int nextMessage;
    private int value;

    public ChoiceDto() {
    }

    public ChoiceDto(int id, String content, int nextMessage, int value) {
        this.id = id;
        this.content = content;
        this.nextMessage = nextMessage;
        this.value = value;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNextMessage() {
        return nextMessage;
    }

    public void setNextMessage(int nextMessage) {
        this.nextMessage = nextMessage;
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
}
