package SpookyScarySkeletons.anwendungslogik.model;

public class Scenario {

    private String name;
    private String websocketEndpoint;
    private String previewImageUrl;
    private String backgroundImageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsocketEndpoint() {
        return websocketEndpoint;
    }

    public void setWebsocketEndpoint(String websocketEndpoint) {
        this.websocketEndpoint = websocketEndpoint;
    }

    public String getPreviewImageUrl() {return previewImageUrl; }

    public void setPreviewImageUrl(String previewImageUrl) { this.previewImageUrl = previewImageUrl; }

    public String getBackgroundImageUrl() { return backgroundImageUrl; }

    public void setBackgroundImageUrl(String backgroundImageUrl) {
        this.backgroundImageUrl = backgroundImageUrl;
    }
}
