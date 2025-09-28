package exception.user;

public class UserRoleNotDeletedException extends RuntimeException {

    public UserRoleNotDeletedException(String message) {
        super(message);
    }

    public UserRoleNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}
