package exception.user;

public class UserRoleNotUpdatedException extends RuntimeException {
    public UserRoleNotUpdatedException(String message) {
        super(message);
    }
}
