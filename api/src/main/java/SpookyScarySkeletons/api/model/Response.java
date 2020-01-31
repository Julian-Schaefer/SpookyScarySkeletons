package SpookyScarySkeletons.api.model;

import SpookyScarySkeletons.api.dto.MessageDTO;

public class Response<T> {

    public enum Type {
        MESSAGE,
        GAME_OVER,
        INFORMATION,
        VALUE_CHANGE,
    }

    private Type type;
    private T content;

    public Response() {
    }

    private Response(Type type, T content) {
        this.type = type;
        this.content = content;
    }

    public static Response<MessageDTO> message(MessageDTO content) {
        return new Response<>(Type.MESSAGE, content);
    }

    public static Response<Void> gameOver() {
        return new Response<>(Type.GAME_OVER, null);
    }

    public static Response<String> information(String content) {
        return new Response<>(Type.INFORMATION, content);
    }

    public static Response<Integer> valueChanged(int content) {
        return new Response<>(Type.VALUE_CHANGE, content);
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
