package SpookyScarySkeletons.api.dto;

public class MessageDTO {

    private int id;
    private String content;
    private ChoiceDTO firstChoice;
    private ChoiceDTO secondChoice;

    public MessageDTO() {

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

    public ChoiceDTO getFirstChoice() {
        return firstChoice;
    }

    public void setFirstChoice(ChoiceDTO firstChoice) {
        this.firstChoice = firstChoice;
    }

    public ChoiceDTO getSecondChoice() {
        return secondChoice;
    }

    public void setSecondChoice(ChoiceDTO secondChoice) {
        this.secondChoice = secondChoice;
    }
}
