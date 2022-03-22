package bll.exception;

public class AdminDAOException extends Throwable{
    private String message;
    public AdminDAOException(String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}