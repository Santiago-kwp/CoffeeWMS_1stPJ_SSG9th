package exception.user;

public class NotRegisteredUserException extends LoginException {

    public NotRegisteredUserException(String message) {
        super(message);
    }
}
