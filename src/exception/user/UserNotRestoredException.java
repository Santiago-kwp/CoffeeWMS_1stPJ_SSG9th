package exception.user;

public class UserNotRestoredException extends IllegalStateException {

    public UserNotRestoredException(String message) {
        super(message);
    }

    public UserNotRestoredException(String message, Throwable cause) {
        super(message, cause);
    }
}
