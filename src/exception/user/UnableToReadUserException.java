package exception.user;

public class UnableToReadUserException extends IllegalStateException {

    public UnableToReadUserException(String message) {
        super(message);
    }

    public UnableToReadUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
