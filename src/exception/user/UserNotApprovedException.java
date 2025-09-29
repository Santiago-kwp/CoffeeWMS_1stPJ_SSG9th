package exception.user;

public class UserNotApprovedException extends IllegalStateException {

    public UserNotApprovedException(String message) {
        super(message);
    }

}
