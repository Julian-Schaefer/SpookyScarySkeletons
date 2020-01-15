package SpookyScarySkeletons.api.dto;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class ChoiceDTO {

    private int id;
    private String content;

    public ChoiceDTO() {
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

    public static ChoiceDTO getChoiceDTOFromJSONString(String jsonString) {
        JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();
        ChoiceDTO choiceDTO = new ChoiceDTO();
        choiceDTO.setId(jsonObject.getInt("id"));
        choiceDTO.setContent(jsonObject.getString("content"));
        return choiceDTO;
    }
}
