package exception.user;

public class FailedToApproveUserException extends IllegalStateException {

    public FailedToApproveUserException(String message) {
        super(message);
    }

}
