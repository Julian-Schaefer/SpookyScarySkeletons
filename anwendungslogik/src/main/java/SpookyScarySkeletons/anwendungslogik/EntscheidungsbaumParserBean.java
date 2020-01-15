package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.anwendungslogik.xmlDtos.ChoiceXmlDTO;
import SpookyScarySkeletons.anwendungslogik.xmlDtos.MessageXmlDTO;
import SpookyScarySkeletons.anwendungslogik.xmlDtos.MessageTreeXmlDTO;

import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class EntscheidungsbaumParserBean {

    private List<Message> messages = new LinkedList<>();
    private List<Choice> choices = new LinkedList<>();
    private MessageTreeXmlDTO messageTreeXmlDTO = null;

    public Message buildTree(String path) {
        InputStream inputStream = getClass().getResourceAsStream(path);

        // parsen
        try {
            String xml = inputStreamToString(inputStream);
            JAXBContext context = JAXBContext.newInstance(MessageTreeXmlDTO.class);
            Unmarshaller um = context.createUnmarshaller();
            messageTreeXmlDTO = (MessageTreeXmlDTO) um.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        if(messageTreeXmlDTO == null)
            return null;

        // Messages und Choices erstellen
        messageTreeXmlDTO.getChoices()
                .stream()
                .forEach(choiceXmlDTO -> choices.add(new Choice(choiceXmlDTO.getId(), choiceXmlDTO.getContent(), null, choiceXmlDTO.getValueChange(), choiceXmlDTO.getMinValue())));
        messageTreeXmlDTO.getMessages()
                .stream()
                .forEach(messageXmlDTO -> messages.add(new Message(messageXmlDTO.getId(), messageXmlDTO.getContent(), null, null)));

        // Referenzen nachtragen
        messages.stream()
                .forEach(message -> {
                    MessageXmlDTO dto = findMessageDtoById(message.getId());
                    message.setFirstChoice(findChoiceById(dto.getFirstChoice()));
                    message.setSecondChoice(findChoiceById(dto.getSecondChoice()));
                });
        choices.stream()
                .forEach(choice -> {
                    ChoiceXmlDTO dto = findChoiceDtoById(choice.getId());
                    choice.setNextMessage(findMessageById(dto.getNextMessage()));
                });
        Message a = findMessageById(0);
        return findMessageById(0);
    }

    private String inputStreamToString(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    private Choice findChoiceById(int id) {
        for(Choice choice: choices) {
            if (choice.getId() == id)
                return choice;
        }
        return null;
    }

    private Message findMessageById(int id) {
        for(Message message: messages) {
            if (message.getId() == id)
                return message;
        }
        return null;
    }

    private MessageXmlDTO findMessageDtoById(int id) {
        for(MessageXmlDTO message: messageTreeXmlDTO.getMessages()) {
            if (message.getId() == id)
                return message;
        }
        return null;
    }

    private ChoiceXmlDTO findChoiceDtoById(int id) {
        for(ChoiceXmlDTO choice: messageTreeXmlDTO.getChoices()) {
            if (choice.getId() == id)
                return choice;
        }
        return null;
    }
}
