package bll.exception;

public class LoginException  extends Throwable {
    private String message;

    public LoginException(String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
