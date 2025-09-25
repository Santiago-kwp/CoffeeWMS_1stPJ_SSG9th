package exception.user;

public class IDNotFoundException extends LoginException {

    public IDNotFoundException(String message) {
        super(message);
    }
}
