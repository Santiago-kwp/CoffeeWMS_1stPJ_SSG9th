package exception.user;

public class NotUpdatedMemberException extends RuntimeException {

    public NotUpdatedMemberException(String message) {
        super(message);
    }
}
