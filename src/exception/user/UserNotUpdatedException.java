package exception.user;

public class UserNotUpdatedException extends RuntimeException {

    public UserNotUpdatedException(String message) {
        super(message);
    }

    public UserNotUpdatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
