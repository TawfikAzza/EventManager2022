package bll.exception;

public class EventDAOException extends Throwable{
    private String message;
    public EventDAOException(String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
