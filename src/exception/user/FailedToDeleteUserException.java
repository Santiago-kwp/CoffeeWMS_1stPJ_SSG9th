package exception.user;

public class FailedToDeleteUserException extends RuntimeException {

    public FailedToDeleteUserException(String message) {
        super(message);
    }

    public FailedToDeleteUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
