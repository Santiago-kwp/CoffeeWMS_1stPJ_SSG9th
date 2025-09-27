package exception.user;

public class UserNotRestoredException extends RuntimeException {

    public UserNotRestoredException(String message) {
        super(message);
    }
}
