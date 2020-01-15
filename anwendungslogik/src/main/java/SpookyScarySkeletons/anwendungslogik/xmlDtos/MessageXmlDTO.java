package SpookyScarySkeletons.anwendungslogik.xmlDtos;

public class MessageXmlDTO {
    private int id;
    private String content;
    private int firstChoice;
    private int secondChoice;

    public MessageXmlDTO() {
    }

    public MessageXmlDTO(int id, String content, int firstChoice, int secondChoice) {
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

    public int getFirstChoice() {
        return firstChoice;
    }

    public void setFirstChoice(int firstChoice) {
        this.firstChoice = firstChoice;
    }

    public int getSecondChoice() {
        return secondChoice;
    }

    public void setSecondChoice(int secondChoice) {
        this.secondChoice = secondChoice;
    }
}
