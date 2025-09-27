package exception.user;

public class UserNotApprovedException extends RuntimeException {

    public UserNotApprovedException(String message) {
        super(message);
    }
}
