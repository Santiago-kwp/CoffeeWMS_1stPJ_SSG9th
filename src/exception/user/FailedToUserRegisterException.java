package exception.user;

public class FailedToUserRegisterException extends IllegalStateException {

    public FailedToUserRegisterException(String message) {
        super(message);
    }

}
