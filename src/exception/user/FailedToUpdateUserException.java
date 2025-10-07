package exception.user;

public class FailedToUpdateUserException extends RuntimeException {

    public FailedToUpdateUserException(String message) {
        super(message);
    }

    public FailedToUpdateUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
