package SpookyScarySkeletons.anwendungslogik.xmlDtos;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "tree")
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageTreeXmlDTO {
    @XmlElementWrapper(name = "messages")
    @XmlElement(name = "message")
    private List<MessageXmlDTO> messages;

    @XmlElementWrapper(name = "choices")
    @XmlElement(name = "choice")
    private List<ChoiceXmlDTO> choices;

    public List<MessageXmlDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageXmlDTO> messages) {
        this.messages = messages;
    }

    public List<ChoiceXmlDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceXmlDTO> choices) {
        this.choices = choices;
    }
}
