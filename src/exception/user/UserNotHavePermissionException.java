package exception.user;

public class UserNotHavePermissionException extends RuntimeException {
    public UserNotHavePermissionException(String message) {
        super(message);
    }
}
