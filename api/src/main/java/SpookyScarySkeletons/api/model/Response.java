package SpookyScarySkeletons.api.model;

public class Response<T> {

    public enum Type {
        MESSAGE,
        INFORMATION,
        VALUE_CHANGE
    }

    private Type type;
    private T content;

    public Response() {
    }

    public Response(Type type, T content) {
        this.type = type;
        this.content = content;
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
