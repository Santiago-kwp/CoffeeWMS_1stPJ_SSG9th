package exception.user;

public class FailedToAccessLoginDataException extends RuntimeException {

    public FailedToAccessLoginDataException(String message) {
        super(message);
    }

    public FailedToAccessLoginDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
