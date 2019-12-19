package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Choice;
import SpookyScarySkeletons.anwendungslogik.model.Message;
import SpookyScarySkeletons.anwendungslogik.xmlDtos.ChoiceDto;
import SpookyScarySkeletons.anwendungslogik.xmlDtos.MessageDto;
import SpookyScarySkeletons.anwendungslogik.xmlDtos.TreeDto;

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
    private TreeDto tree = null;

    public Message buildTree(String path) {
        File file = new File(path);

        // parsen
        try {
            String xml = fileToString(file);
            JAXBContext context = JAXBContext.newInstance(TreeDto.class);
            Unmarshaller um = context.createUnmarshaller();
            tree = (TreeDto) um.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        if(tree == null)
            return null;

        // Messages und Choices erstellen
        tree.getChoices()
                .stream()
                .forEach(choiceDto -> choices.add(new Choice(choiceDto.getId(), choiceDto.getContent(), null, choiceDto.getValue())));
        tree.getMessages()
                .stream()
                .forEach(messageDto -> messages.add(new Message(messageDto.getId(), messageDto.getContent(), null, null)));

        // Referenzen nachtragen
        messages.stream()
                .forEach(message -> {
                    MessageDto dto = findMessageDtoById(message.getId());
                    message.setFirstChoice(findChoiceById(dto.getFirstChoice()));
                    message.setSecondChoice(findChoiceById(dto.getSecondChoice()));
                });
        choices.stream()
                .forEach(choice -> {
                    ChoiceDto dto = findChoiceDtoById(choice.getId());
                    choice.setNextMessage(findMessageById(dto.getNextMessage()));
                });
        Message a = findMessageById(0);
        return findMessageById(0);
    }

    private String fileToString(File file) {
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
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

    private MessageDto findMessageDtoById(int id) {
        for(MessageDto message: tree.getMessages()) {
            if (message.getId() == id)
                return message;
        }
        return null;
    }

    private ChoiceDto findChoiceDtoById(int id) {
        for(ChoiceDto choice: tree.getChoices()) {
            if (choice.getId() == id)
                return choice;
        }
        return null;
    }
}
