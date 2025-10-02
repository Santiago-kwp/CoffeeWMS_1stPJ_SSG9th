package exception.user;

public class UserDeleteFailedException extends RuntimeException {

    public UserDeleteFailedException(String message) {
        super(message);
    }

    public UserDeleteFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
