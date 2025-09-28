package exception.user;

public class UserNotHavePermissionException extends IllegalStateException {
    public UserNotHavePermissionException(String message) {
        super(message);
    }
}
