package exception.user;

public class FailedToDeleteUserRoleException extends RuntimeException {

    public FailedToDeleteUserRoleException(String message) {
        super(message);
    }

}
