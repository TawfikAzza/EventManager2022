package bll.exception;

public class EventManagerException extends Throwable {
    private String message;

    public EventManagerException(String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}