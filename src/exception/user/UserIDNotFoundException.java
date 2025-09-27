package exception.user;

public class UserIDNotFoundException extends LoginException {

    public UserIDNotFoundException(String message) {
        super(message);
    }
}
