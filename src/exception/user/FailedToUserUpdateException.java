package exception.user;

public class FailedToUserUpdateException extends RuntimeException {

    public FailedToUserUpdateException(String message) {
        super(message);
    }

    public FailedToUserUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
