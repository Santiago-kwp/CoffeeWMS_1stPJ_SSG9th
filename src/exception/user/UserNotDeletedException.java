package exception.user;

public class UserNotDeletedException extends RuntimeException {

    public UserNotDeletedException(String message) {
        super(message);
    }

    public UserNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
