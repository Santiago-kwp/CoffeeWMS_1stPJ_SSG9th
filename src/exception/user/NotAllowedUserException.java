package exception.user;

public class NotAllowedUserException extends IllegalStateException {

    public NotAllowedUserException(String message) {
        super(message);
    }

}
