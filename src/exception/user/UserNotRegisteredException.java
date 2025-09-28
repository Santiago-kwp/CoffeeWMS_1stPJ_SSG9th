package exception.user;

public class UserNotRegisteredException extends IllegalStateException {

    public UserNotRegisteredException(String message) {
        super(message);
    }

    public UserNotRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
