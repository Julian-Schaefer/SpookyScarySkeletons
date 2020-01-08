package SpookyScarySkeletons.anwendungslogik.xmlDtos;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "tree")
@XmlAccessorType(XmlAccessType.FIELD)
public class TreeDto {
    @XmlElementWrapper(name = "messages")
    @XmlElement(name = "message")
    private List<MessageDto> messages;

    @XmlElementWrapper(name = "choices")
    @XmlElement(name = "choice")
    private List<ChoiceDto> choices;

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

    public List<ChoiceDto> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceDto> choices) {
        this.choices = choices;
    }
}
