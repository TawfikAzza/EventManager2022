package bll.exception;

public class AdminLogicException  extends Throwable {
    private String message;

    public AdminLogicException(String message, Exception e) {
        this.message = message;
        e.printStackTrace();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
