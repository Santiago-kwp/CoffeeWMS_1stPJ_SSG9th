package exception.user;

public class UserNotDeletedException extends RuntimeException {

    public UserNotDeletedException(String message) {
        super(message);
    }
}
