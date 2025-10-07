package exception.user;

public class FailedToRestoreUserException extends FailedToUpdateUserException {

    public FailedToRestoreUserException(String message) {
        super(message);
    }

}
