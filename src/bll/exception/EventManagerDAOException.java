package bll.exception;

public class EventManagerDAOException extends Throwable {
    private String message;

    public EventManagerDAOException(String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}