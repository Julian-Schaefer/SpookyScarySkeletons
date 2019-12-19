package SpookyScarySkeletons.anwendungslogik.xmlDtos;

import SpookyScarySkeletons.anwendungslogik.model.Choice;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "tree")
@XmlAccessorType(XmlAccessType.FIELD)
public class TreeDto {
    @XmlElementWrapper(name = "messages")
    @XmlElement(name = "message")
    List<MessageDto> messages;

    @XmlElementWrapper(name = "choices")
    @XmlElement(name = "choice")
    List<ChoiceDto> choices;

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
