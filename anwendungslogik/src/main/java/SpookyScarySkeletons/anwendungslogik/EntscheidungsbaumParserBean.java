package SpookyScarySkeletons.anwendungslogik;

import SpookyScarySkeletons.anwendungslogik.model.Message;

import javax.ejb.Stateless;
import java.io.File;

@Stateless
public class EntscheidungsbaumParserBean {

    public Message buildTree(String path) {
        File file = new File(path);
        System.out.println("File gefunden: " + file.getAbsoluteFile());
        return null;
    }
}
