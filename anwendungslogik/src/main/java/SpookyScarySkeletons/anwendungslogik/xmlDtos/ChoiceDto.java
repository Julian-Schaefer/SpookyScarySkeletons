package SpookyScarySkeletons.anwendungslogik.xmlDtos;

public class ChoiceDto {
    private int id;
    private String content;
    private int nextMessage;
    private int valueChange;
    private int minValue;

    public ChoiceDto() {
    }

    public ChoiceDto(int id, String content, int nextMessage, int valueChange, int minValue) {
        this.id = id;
        this.content = content;
        this.nextMessage = nextMessage;
        this.valueChange = valueChange;
        this.minValue = minValue;
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

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }
}
