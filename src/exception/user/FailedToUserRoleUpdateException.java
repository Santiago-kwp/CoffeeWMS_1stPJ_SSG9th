package exception.user;

public class FailedToUserRoleUpdateException extends RuntimeException {
    public FailedToUserRoleUpdateException(String message) {
        super(message);
    }

}
