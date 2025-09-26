package exception.user;

public class NotHavePermissionException extends RuntimeException {
    public NotHavePermissionException(String message) {
        super(message);
    }
}
