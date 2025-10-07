package exception.user;

public class FailedToLoginException extends RuntimeException {

    public FailedToLoginException(String message) {
        super(message);
    }

}
