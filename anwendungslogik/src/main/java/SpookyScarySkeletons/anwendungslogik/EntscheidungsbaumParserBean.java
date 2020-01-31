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
    
    public Message buildTree(String path) {
        List<Message> messages = new LinkedList<>();
        List<Choice> choices = new LinkedList<>();
        MessageTreeXmlDTO messageTreeXmlDTO = null;

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
                .forEach(choiceXmlDTO -> choices.add(new Choice(choiceXmlDTO.getId(), choiceXmlDTO.getContent().trim(), null, choiceXmlDTO.getValueChange(), choiceXmlDTO.getMinValue())));
        messageTreeXmlDTO.getMessages()
                .stream()
                .forEach(messageXmlDTO -> messages.add(new Message(messageXmlDTO.getId(), messageXmlDTO.getContent().trim(), null, null)));

        // Referenzen nachtragen
        final MessageTreeXmlDTO finalMessageTreeXMLDTO = messageTreeXmlDTO;

        messages.stream()
                .forEach(message -> {
                    MessageXmlDTO dto = findMessageDtoById(message.getId(), finalMessageTreeXMLDTO);
                    message.setFirstChoice(findChoiceById(dto.getFirstChoice(), choices));
                    message.setSecondChoice(findChoiceById(dto.getSecondChoice(), choices));
                });
        choices.stream()
                .forEach(choice -> {
                    ChoiceXmlDTO dto = findChoiceDtoById(choice.getId(), finalMessageTreeXMLDTO);
                    choice.setNextMessage(findMessageById(dto.getNextMessage(), messages));
                });
        
        return findMessageById(0, messages);
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

    private Choice findChoiceById(int id, List<Choice> choices) {
        for(Choice choice: choices) {
            if (choice.getId() == id)
                return choice;
        }
        return null;
    }

    private Message findMessageById(int id, List<Message> messages) {
        for(Message message: messages) {
            if (message.getId() == id)
                return message;
        }
        return null;
    }

    private MessageXmlDTO findMessageDtoById(int id, MessageTreeXmlDTO messageTreeXmlDTO) {
        for(MessageXmlDTO message: messageTreeXmlDTO.getMessages()) {
            if (message.getId() == id)
                return message;
        }
        return null;
    }

    private ChoiceXmlDTO findChoiceDtoById(int id, MessageTreeXmlDTO messageTreeXmlDTO) {
        for(ChoiceXmlDTO choice: messageTreeXmlDTO.getChoices()) {
            if (choice.getId() == id)
                return choice;
        }
        return null;
    }
}
