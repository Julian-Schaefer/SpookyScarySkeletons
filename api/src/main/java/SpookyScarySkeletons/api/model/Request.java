package SpookyScarySkeletons.api.model;

public class Request {

    public enum Type {
        CHOICE
    }

    private Type type;
    private Object content;

    public Request() {
    }

    public Request(Type type, Object content) {
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

    public void setContent(Object content) {
        this.content = content;
    }
}
