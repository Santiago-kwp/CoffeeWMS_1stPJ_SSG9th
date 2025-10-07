package exception.user;

public class FailedToAccessUserDataException extends RuntimeException {

    public FailedToAccessUserDataException(String message) {
        super(message);
    }

    public FailedToAccessUserDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
