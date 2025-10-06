package exception.transaction;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}