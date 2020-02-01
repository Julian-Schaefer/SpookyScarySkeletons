package SpookyScarySkeletons.api.dto;

public class GameOverDTO {

    private boolean won;
    private String message;

    public GameOverDTO() {
    }

    public GameOverDTO(boolean won, String message) {
        this.won = won;
        this.message = message;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
