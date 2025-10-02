package exception.user;

public class FailedToUserRestoreException extends IllegalStateException {

    public FailedToUserRestoreException(String message) {
        super(message);
    }

}
