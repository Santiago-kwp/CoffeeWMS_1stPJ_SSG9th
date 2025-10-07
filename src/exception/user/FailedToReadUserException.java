package exception.user;

public class FailedToReadUserException extends IllegalStateException {

    public FailedToReadUserException(String message) {
        super(message);
    }

}
