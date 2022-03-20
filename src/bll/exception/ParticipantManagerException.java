package bll.exception;

public class ParticipantManagerException extends Throwable {
    private String message;

    public ParticipantManagerException(String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
