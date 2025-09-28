package exception.user;

public class UserNotHavePermissionException extends IllegalStateException {

    public UserNotHavePermissionException(String message) {
        super(message);
    }

    public UserNotHavePermissionException(String message, Throwable cause) {
        super(message, cause);
    }
}
