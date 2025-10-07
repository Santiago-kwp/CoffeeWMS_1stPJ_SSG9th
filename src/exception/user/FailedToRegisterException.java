package exception.user;

public class FailedToRegisterException extends IllegalStateException {

    public FailedToRegisterException(String message) {
        super(message);
    }

}
